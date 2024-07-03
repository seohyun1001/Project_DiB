package org.zerock.project_dib.restaurant.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class FileController {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        // 파일 받아오기("C:\\img\\aaa.jpg")
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();
        //headers를 try안과 밖에서 모두 사용하기 위해 밖에 선언
        HttpHeaders headers = new HttpHeaders();
        try {
            //이미지 파일의 Content-Type을 설정 : image/jpeg
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch(IOException e) {
            //에러 발생시 에러 스테이터스 전달 = 500에러 코드 실행시 에러
            return ResponseEntity.internalServerError().build();
        }
        //정상실행시 파일과 정상 스테이터스 전달 200:정상 실행
        return ResponseEntity.ok().headers(headers).body(resource);
    }
}
