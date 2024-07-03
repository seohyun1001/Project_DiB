package org.zerock.project_dib.member.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.project_dib.member.security.exception.RefreshTokenException;
import org.zerock.project_dib.member.util.JWTUtil;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class RefreshTokenFilter extends OncePerRequestFilter {

    private final String refreshToken;

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 요청 url 취득, path에 저장
        String path = request.getRequestURI();

        // 요청 url이 refreshPath가 아니면 필터로 처리하지 끝냄
        if(!path.equals(refreshToken)) {
            log.info("skip refresh token filter---------------");
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Refresh token filter --- run ---------------1");

        // 전송된 JSON에서 accessToken과 refreshToken을 얻어온다
        Map<String, String> tokens = parseRequestJSON(request);

        // access
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");

        log.info("accessToken : " + accessToken);
        log.info("refreshToken : " + refreshToken);
        
        try {
            checkAccessToken(accessToken);
        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }

        // try문 안에서 선언 변수는 try문 밖에서 사용할 수 없음 -> 밖에서 선언 먼저
        Map<String, Object> refreshClaims = null;

        try {
            // refreshToken의 확인
            refreshClaims = checkRefreshToken(refreshToken);
            log.info(refreshClaims);

            // 토큰의 만료기한을 변수에 저장
            // Refresh Token의 유효 시간이 얼마 남지 않은 경우
            Integer exp = (Integer) refreshClaims.get("exp");

            // Integer 값으로 저장된 만료기한을 시간타입으로 변환
            Date expTime = new Date(Instant.ofEpochMilli(exp).toEpochMilli() * 1000);
            // 현재시간을 밀리세컨드 단위로 취득
            Date current = new Date(System.currentTimeMillis());

            // 만료 시간과 현재 시간의 간격 계산
            // 만일 3일 미만인 경우에는 Refresh Token도 다시 생성
            long gapTime = (expTime.getTime() - current.getTime());

            log.info("-----------------------------------------------------");
            log.info("current : " + current);
            log.info("expTime : " + expTime);
            log.info("gapTime : " + gapTime);

            // 토큰을 생성할 때 저장할 mid를 취득
            String mid = (String)refreshClaims.get("mid");

            // 이 상태까지 오면 무조건 AccessToken은 새로 생성
            String accessTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 1);
            // 기존에 존재하던 refreshToken을 저장
            String refreshTokenValue = tokens.get("refreshToken");

            // RefreshToken의 gapTime이 3일도 안 남았다면 새로운 refreshToken 생성
            if (gapTime < (1000 * 60 * 60 * 24 * 3)) {
                log.info("new refresh token required");
                refreshTokenValue = jwtUtil.generateToken(Map.of("mid", mid), 30);
            }

            log.info("refresh Token result");
            log.info("accessToken : " + accessTokenValue);
            log.info("refreshToken : " + refreshTokenValue);

            // 요청페이지에 새로운 토큰들을 보내주는 처리
            sendTokens(accessTokenValue, refreshTokenValue, response);

        } catch (RefreshTokenException refreshTokenException) {
            refreshTokenException.sendResponseError(response);
            return;
        }
    }

    // request의 토큰 데이터를 Map<String, String> 으로 변경
    private Map<String, String> parseRequestJSON(HttpServletRequest request) {
        // JSON 데이터를 분석해서 mid, mpw 전달 값을 MAP으로 처리
        try(Reader reader = new InputStreamReader(request.getInputStream())) {
            Gson gson = new Gson();
            return gson.fromJson(reader, Map.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    // 엑세스 토큰이 정상인지 확인하는 메서드
    private void checkAccessToken(String accessToken) throws RefreshTokenException
    {
        try {
            jwtUtil.validateToken(accessToken);
        } catch (ExpiredJwtException e) {
            log.info("Access token has expired");
        } catch (Exception e) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_ACCESS);
        }
    }

    //
    private Map<String, Object> checkRefreshToken(String refreshToken) throws RefreshTokenException{
        try {
            Map<String, Object> values = jwtUtil.validateToken(refreshToken);
            return values;
        } catch (ExpiredJwtException expiredJwtException) {
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.OLD_REFRESH);
        } catch (MalformedJwtException malformedJwtException) {
            log.error("MalformedJwtException--------------------------");
            throw new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        } catch (Exception exception) {
            new RefreshTokenException(RefreshTokenException.ErrorCase.NO_REFRESH);
        }
        return null;
    }

    private void sendTokens(String accessTokenValue, String refreshTokenValue, HttpServletResponse response) {

        // JSON 타입으로 응답하기 위한 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 각 토큰을 JSON 형식으로 변환
        Gson gson = new Gson();
        String jsonStr = gson.toJson(Map.of("accessToken", accessTokenValue, "refreshToken", refreshTokenValue));

        // response에 토큰들을 설정하여 반환할 수 있도록 만들어줌
        try {
            response.getWriter().println(jsonStr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
