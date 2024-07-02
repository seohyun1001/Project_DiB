package org.zerock.project_dib.restaurant.dto;

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
    private int size = 10;
    private Integer rno;
    private String rest_name;
    private String rest_loc;
    private String rest_menu;
    private String keyword;
    private String filter;

    public int getOffset() {
        return (page - 1) * size;
    }

    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=").append(this.page);
        builder.append("&size=").append(this.size);
        if (rno != null) {
            builder.append("&rno=").append(rno);
        }
        if (rest_name != null && !rest_name.isEmpty()) {
            builder.append("&rest_name=").append(rest_name);
        }
        if (rest_loc != null && !rest_loc.isEmpty()) {
            builder.append("&rest_loc=").append(rest_loc);
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
        if ("rest_name".equals(filter)) {
            this.rest_name = keyword;
        } else if ("rest_loc".equals(filter)) {
            this.rest_loc = keyword;
        } else if ("rest_menu".equals(filter)) {
            this.rest_menu = keyword;

        }
    }
}
