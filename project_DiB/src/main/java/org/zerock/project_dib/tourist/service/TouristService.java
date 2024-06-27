package org.zerock.project_dib.tourist.service;

import org.zerock.project_dib.tourist.domain.Tourist;
import org.zerock.project_dib.tourist.domain.TouristImg;

import java.util.List;

public interface TouristService {

    List<Tourist> getList();

    Tourist read(int tno);

    void register(Tourist tourist);

    void modify(Tourist tourist);

    void remove(int tno);

    List<TouristImg> getImgList(int tno);

    void registerImg(TouristImg touristImg);

    void removeImgs(int tno);
}
