package org.zerock.project_dib.tourist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.tourist.domain.Tourist;
import org.zerock.project_dib.tourist.domain.TouristImg;
import org.zerock.project_dib.tourist.mapper.TouristMapper;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TouristServiceImpl implements TouristService {

    private final TouristMapper touristMapper;

    @Override
    public List<Tourist> getList() {
        return touristMapper.getList();
    }

    @Override
    public Tourist read(int tno) {
        return touristMapper.read(tno);
    }

    @Override
    public void register(Tourist tourist) {
        touristMapper.insert(tourist);
    }

    @Override
    public void modify(Tourist tourist) {
        touristMapper.update(tourist);
    }

    @Override
    public void remove(int tno) {
        touristMapper.delete(tno);
    }

    @Override
    public List<TouristImg> getImgList(int tno) {
        return touristMapper.getImgList(tno);
    }

    @Override
    public void registerImg(TouristImg touristImg) {
        touristMapper.insertImg(touristImg);
    }

    @Override
    public void removeImgs(int tno) {
        touristMapper.deleteImgs(tno);
    }
}
