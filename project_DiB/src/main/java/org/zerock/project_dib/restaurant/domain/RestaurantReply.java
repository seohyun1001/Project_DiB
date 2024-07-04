package org.zerock.project_dib.restaurant.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantReply {
    private int review_no;
    private String review_text;
    private String mid;  // Foreign key from member table
    private boolean review_like;
    private int rno;

    // 댓글 수정시, 메서드 이용해서, 댓글 내용만 변경하기.
    public void changeText(String text) {
        this.review_text = text;
    }
}
