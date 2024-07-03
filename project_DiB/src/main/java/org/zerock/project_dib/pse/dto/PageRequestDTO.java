package org.zerock.project_dib.pse.dto;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    @Min(value = 1)
    @Positive
    private int page = 1; // 현재 페이지 번호

    @Builder.Default
    @Min(value = 8) // 최소값을 8
    @Max(value = 100)
    @Positive
    private int size = 8; // 페이지당 데이터 수

    //Notice
    private Long nno;
    private String noticeTitle;
    private String noticeContent;

    //FAQ
    private Long fno;
    private String faqTitle;
    private String faqContent;

    //1대1 문의
    private Long otono;
    private String one2oneTitle;
    private String one2oneContent;
    private String mid;

    @Getter
    private int offset;

    private String[] types;
    private String type;
    private String keyword;
    private boolean finished;
    private LocalDate from;
    private LocalDate to;

    // offset 계산 로직
    public void calculateOffset() {
        if (this.page < 1) {
            this.page = 1; // page 값이 1보다 작으면 1로 설정
        }
        this.offset = (page - 1) * size;
    }
    // offset 관련된 코드들은 페이지네이션을 구현하는데 사용됨
    // offset 값은 데이터베이스 쿼리에서 특정 위치에서부터 데이터를 가져오도록 하기 위함


    // 페이지 건너뛰기 계산
    public int getSkip() {
        return (page - 1) * size;
    }

    // URL 링크 생성
    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=").append(this.page);
        builder.append("&size=").append(this.size);
        if (finished) {
            builder.append("&finished=on");
        }
        if (types != null && types.length > 0) {
            for (String type : types) {
                builder.append("&types=").append(type);
            }
        }
        if (keyword != null) {
            try {
                builder.append("&keyword=").append(URLEncoder.encode(keyword, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (from != null) {
            builder.append("&from=").append(from.toString());
        }
        if (to != null) {
            builder.append("&to=").append(to.toString());
        }
        return builder.toString();
    }

    // 타입 체크
    public boolean checkType(String type) {
        if (types == null || types.length == 0) {
            return false;
        }
        return Arrays.stream(types).anyMatch(type::equals);
    }


}
