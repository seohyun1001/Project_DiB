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
    private Long nno;
    private String noticeTitle;
    private String noticeContent;

    @Getter
    private int offset;

    private String[] types;
    private String type;
    private String keyword;
    private boolean finished;
    private LocalDate from;
    private LocalDate to;


    // offset 계산 로직 추가
    private void calculateOffset() {
        this.offset = (page - 1) * size;
    }

    // 페이지 건너뛰기 계산
    public int getSkip() {
        return (page - 1) * size;
    }

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

    public boolean checkType(String type) {
        if (types == null || types.length == 0) {
            return false;
        }
        return Arrays.stream(types).anyMatch(type::equals);
    }


}
