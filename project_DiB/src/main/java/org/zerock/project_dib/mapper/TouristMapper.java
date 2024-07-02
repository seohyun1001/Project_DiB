package org.zerock.project_dib.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.zerock.project_dib.tourist.domain.Tourist;
import org.zerock.project_dib.tourist.domain.TouristImg;

import java.util.List;

@Mapper
public interface TouristMapper {

    List<Tourist> getList();

    Tourist read(int tno);

    @Options(useGeneratedKeys = true, keyProperty = "tno")
    void insert(Tourist tourist);

    void update(Tourist tourist);

    void delete(int tno);

    List<TouristImg> getImgList(int tno);

    void insertImg(TouristImg touristImg);

    void deleteImgs(int tno);
}
