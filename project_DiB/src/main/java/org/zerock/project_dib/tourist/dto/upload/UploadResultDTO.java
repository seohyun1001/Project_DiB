package org.zerock.project_dib.tourist.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.project_dib.tourist.domain.TouristImg;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
  private String uuid;
  private String fileName;
  private  boolean img;
  public String getLink(){
    if(img){
      return "s_"+uuid+"_"+fileName;
    }else{
      return uuid+"_"+fileName;
    }
  }
  private int tno;
  private int ord;

  public TouristImg toEntity() {
    var touristImg = new TouristImg();
    touristImg.setUuid(uuid);
    touristImg.setFileName(fileName);
    touristImg.setTno(tno);
    touristImg.setOrd(ord);
    return touristImg;
  }
}














