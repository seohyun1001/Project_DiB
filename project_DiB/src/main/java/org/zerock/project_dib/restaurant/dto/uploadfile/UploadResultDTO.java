package org.zerock.project_dib.restaurant.dto.uploadfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    private String uuid;
    private String fileName;
    private int ord;
    private boolean img;

    public String getLink(){
        if(img){
            return "s_"+uuid+"_"+fileName;
        }else{
            return uuid+"_"+fileName;
        }
    }
}
