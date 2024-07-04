package org.zerock.project_dib.tourist.service;

import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.tourist.dto.PageRequestDTO;
import org.zerock.project_dib.tourist.dto.PageResponseDTO;
import org.zerock.project_dib.tourist.dto.TouristDTO;
import org.zerock.project_dib.tourist.dto.TouristImgDTO;

import java.io.IOException;
import java.util.List;

public interface TouristService {

    List<TouristDTO> getList();

    TouristDTO read(int tno);

    int register(TouristDTO touristDTO);

    void modify(TouristDTO touristDTO);

    void remove(int tno);

    List<String> getImgList(int tno);

    void registerImg(TouristDTO touristDTO, MultipartFile file) throws IOException; // TouristImgDTO 삭제, TouristDTO로 수정

    void modifyImg(TouristDTO touristDTO, MultipartFile file) throws IOException;

    void removeImgs(int tno);

    PageResponseDTO<TouristDTO> search(PageRequestDTO pageRequestDTO);
}