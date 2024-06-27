package org.zerock.project_dib.accommodation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.zerock.project_dib.accommodation.domain.AccommodationVO;
import org.zerock.project_dib.accommodation.dto.AccommodationDTO;
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
    public void insertAccommodation(AccommodationDTO accommodationDTO) {

        AccommodationVO accommodationVO = modelMapper.map(accommodationDTO, AccommodationVO.class);
        accommodationMapper.insertAccommodation(accommodationVO);

    }

    @Override
    public List<AccommodationDTO> accList() {
        List<AccommodationDTO> result = accommodationMapper.findAll().stream()
                .map(vo -> modelMapper.map(vo, AccommodationDTO.class))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public AccommodationDTO accInfo(int ano) {

        return modelMapper.map(accommodationMapper.selectOne(ano), AccommodationDTO.class);
    }

}
