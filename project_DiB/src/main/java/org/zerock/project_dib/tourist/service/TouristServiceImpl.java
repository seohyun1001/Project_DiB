package org.zerock.project_dib.tourist.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.project_dib.tourist.domain.Tourist;
import org.zerock.project_dib.tourist.domain.TouristImg;
import org.zerock.project_dib.mapper.TouristMapper;
import org.zerock.project_dib.tourist.dto.TouristDTO;
import org.zerock.project_dib.tourist.dto.TouristImgDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TouristServiceImpl implements TouristService {

    private final TouristMapper touristMapper;

    @Override
    public List<TouristDTO> getList() {
        List<Tourist> tourists = touristMapper.getList();
        return tourists.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public TouristDTO read(int tno) {
        Tourist tourist = touristMapper.read(tno);
        return toDTO(tourist);
    }

    @Override
    public void register(TouristDTO touristDTO) {
        Tourist tourist = toEntity(touristDTO);

//        tourist.setModDate(LocalDateTime.now());

        touristMapper.insert(tourist);
    }

    @Override
    public void modify(TouristDTO touristDTO) {
        Tourist tourist = toEntity(touristDTO);

//        tourist.setModDate(LocalDateTime.now());

        touristMapper.update(tourist);
    }

    @Override
    public void remove(int tno) {
        touristMapper.delete(tno);
    }

    @Override
    public List<TouristImgDTO> getImgList(int tno) {
        List<TouristImg> images = touristMapper.getImgList(tno);
        return images.stream().map(this::toImgDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void registerImg(TouristDTO touristDTO, MultipartFile file) throws IOException {
        Tourist tourist = toEntity(touristDTO); // TouristDTO를 Tourist 엔티티로 변환

        // TouristImg 엔티티 생성 및 설정
        TouristImg touristImg = new TouristImg();
        touristImg.setTno(tourist.getTno()); // 관광지 번호 설정
        touristImg.setFileName(file.getOriginalFilename()); // 파일명 설정
        touristImg.setUuid(UUID.randomUUID().toString()); // UUID 생성 로직 사용

        // 이미지 파일을 byte 배열로 변환하여 설정
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            touristImg.setImage(bytes);
        }

        // 관광지 이미지 등록
        touristMapper.insertImg(touristImg);
    }

    @Override
    public void removeImgs(int tno) {
        touristMapper.deleteImgs(tno);
    }

    // DTO to Entity and Entity to DTO conversion methods
    private TouristDTO toDTO(Tourist tourist) {
        TouristDTO dto = new TouristDTO();
        dto.setTno(tourist.getTno());
        dto.setTourName(tourist.getTourName());
        dto.setTourAddr(tourist.getTourAddr());
        dto.setTourOpentime(tourist.getTourOpentime());
        dto.setTourClosetime(tourist.getTourClosetime());
        dto.setTourParking(tourist.isTourParking());
        dto.setTourContent(tourist.getTourContent());
        dto.setModDate(tourist.getModDate());
        dto.setRegDate(tourist.getRegDate());
        return dto;
    }

    private Tourist toEntity(TouristDTO dto) {
        Tourist tourist = new Tourist();
        tourist.setTno(dto.getTno());
        tourist.setTourName(dto.getTourName());
        tourist.setTourAddr(dto.getTourAddr());
        tourist.setTourOpentime(dto.getTourOpentime());
        tourist.setTourClosetime(dto.getTourClosetime());
        tourist.setTourParking(dto.isTourParking());
        tourist.setTourContent(dto.getTourContent());
        tourist.setModDate(dto.getModDate());
        tourist.setRegDate(dto.getRegDate());
        return tourist;
    }

    private TouristImgDTO toImgDTO(TouristImg img) {
        TouristImgDTO dto = new TouristImgDTO();
        dto.setUuid(img.getUuid());
        dto.setFileName(img.getFileName());
        dto.setTno(img.getTno());
        dto.setOrd(img.getOrd());
        return dto;
    }
}