package org.zerock.project_dib.accommodation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.project_dib.accommodation.service.AccommodationService;

import java.io.File;

@RestController
@RequestMapping("/accommodation")
@RequiredArgsConstructor
public class AccommodationRestController {

    private final AccommodationService accommodationService;

    @PostMapping("/deleteImg/{ord}")
    public void deleteImg(@PathVariable int ord) {

        String fileName = accommodationService.findAllFileByOrd(ord).getFile_name();

        File fileToDelete = new File("c:\\upload\\" + fileName);

        if (fileToDelete.delete()) {
            System.out.println("파일 삭제 성공: " + fileToDelete.getName());
        } else {
            System.out.println("파일 삭제 실패: " + fileToDelete.getName());
        }

        accommodationService.removeFile(ord);

    }

}
