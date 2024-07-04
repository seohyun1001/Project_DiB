package org.zerock.project_dib.accommodation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

  @Builder.Default
  private int page = 1;

  @Builder.Default
  private int size = 10;

  private String link;

  public int getSkip() {
    return (page - 1) * size;
  }

  public String getLink(){

    if(link == null){
      StringBuilder builder = new StringBuilder();
      builder.append("page="+this.page);
      builder.append("&size="+this.size);

      link = builder.toString();
    }

    return link;
  }


}
















