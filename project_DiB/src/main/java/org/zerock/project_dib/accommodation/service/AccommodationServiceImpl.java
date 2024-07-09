package org.zerock.project_dib.accommodation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.accommodation.domain.AccommodationImgVO;
import org.zerock.project_dib.accommodation.domain.AccommodationVO;
import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
import org.zerock.project_dib.accommodation.dto.AccommodationImgDTO;
import org.zerock.project_dib.accommodation.dto.PageRequestDTO;
import org.zerock.project_dib.accommodation.dto.PageResponseDTO;
import org.zerock.project_dib.mapper.AccommodationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccommodationServiceImpl implements AccommodationService {

    private final ModelMapper modelMapper;

    private final AccommodationMapper accommodationMapper;

    @Override
    public Long insertAccommodation(AccommodationDTO accommodationDTO) {

        AccommodationVO accommodationVO = modelMapper.map(accommodationDTO, AccommodationVO.class);
        accommodationMapper.insertAccommodation(accommodationVO);

        return accommodationVO.getAno();
    }

    @Override
    public List<AccommodationDTO> accList() {

        List<AccommodationDTO> result = accommodationMapper.findAll().stream()
                .map(vo -> modelMapper.map(vo, AccommodationDTO.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public PageResponseDTO<AccommodationDTO> search(PageRequestDTO pageRequestDTO) {

        List<AccommodationVO> voList = accommodationMapper.search(pageRequestDTO);
        List<AccommodationDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, AccommodationDTO.class))
                .collect(Collectors.toList());

        int total = accommodationMapper.getTotalCount(pageRequestDTO);

        return PageResponseDTO.<AccommodationDTO>builder()
                .dtoList(dtoList)
                .total(total)
                .page(pageRequestDTO.getPage())
                .size(pageRequestDTO.getSize())
                .start((int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10 - 9)
                .end((int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10)
                .prev((int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10 - 9 > 1)
                .next(total > (int) (Math.ceil(pageRequestDTO.getPage() / 10.0)) * 10)
                .totalPage((int) Math.ceil((double) total / pageRequestDTO.getSize()))
                .build();
    }

    @Override
    public AccommodationDTO accInfo(Long ano) {

        return modelMapper.map(accommodationMapper.selectOne(ano), AccommodationDTO.class);
    }

    @Override
    public void modify(AccommodationDTO accommodationDTO) {

        accommodationMapper.update(modelMapper.map(accommodationDTO, AccommodationVO.class));

    }

    @Override
    public void remove(Long ano) {

        accommodationMapper.delete(ano);

    }

    @Override
    public void insertFile(AccommodationImgDTO accommodationImgDTO) {

        AccommodationImgVO accommodationImgVO = modelMapper.map(accommodationImgDTO, AccommodationImgVO.class);
        accommodationMapper.insertFile(accommodationImgVO);

    }

    @Override
    public List<AccommodationImgDTO> findAllFileByAno(Long ano) {

        List<AccommodationImgDTO> result = accommodationMapper.findAllFilesByAno(ano).stream()
                .map(vo -> modelMapper.map(vo, AccommodationImgDTO.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public AccommodationImgDTO findAllFileByOrd() {

        return modelMapper.map(accommodationMapper.findFileByFirstOrd(), AccommodationImgDTO.class);
    }

    @Override
    public AccommodationImgDTO findFileByUuid(String uuid) {

        return modelMapper.map(accommodationMapper.findFileByUuid(uuid), AccommodationImgDTO.class);
    }

    @Override
    public  List<AccommodationImgDTO> findAllFiles() {

        List<AccommodationImgDTO> result = accommodationMapper.findAllFiles().stream()
                .map(vo -> modelMapper.map(vo, AccommodationImgDTO.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public void removeFile(String uuid) {

        accommodationMapper.deleteFile(uuid);

    }

    @Override
    public AccommodationImgDTO findOrdByAno(Long ano) {
        AccommodationImgVO vo = accommodationMapper.findOrdByAno(ano);
        AccommodationImgDTO dto = null;
        if (vo != null) {
            dto = modelMapper.map(vo, AccommodationImgDTO.class);
        }
        return dto;
    }

}
