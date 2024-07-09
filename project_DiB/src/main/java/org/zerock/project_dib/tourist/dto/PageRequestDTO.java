package org.zerock.project_dib.tourist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 15;

    private Integer tno;
    private String tourName;
    private String tourAddr;
    private String keyword;
    private String filter;

    private int getOffset() {
        return (page - 1) * size;
    }

    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=").append(this.page);
        builder.append("&size=").append(this.size);

        if (tno != null) {
            builder.append("&tno=").append(tno);
        }
        if (tourName != null && !tourName.isEmpty()) {
            builder.append("&tourName=").append(tourName);
        }
        if (tourAddr != null && !tourAddr.isEmpty()) {
            builder.append("&tourAddr=").append(tourAddr);
        }
        if (keyword != null && !keyword.isEmpty()) {
            try {
                builder.append("&keyword=").append(URLEncoder.encode(keyword, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (filter != null && !filter.isEmpty()) {
            builder.append("&filter=").append(filter);
        }
        return builder.toString();
    }

    public void setFilterAndKeyword() {
        if ("tourName".equals(filter)){
            this.tourName = keyword;
        } else if ("tourAddr".equals(filter)) {
            this.tourAddr = keyword;
        }
    }
}
