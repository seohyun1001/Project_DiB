package org.zerock.project_dib;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "org.zerock.project_dib.mapper")
public class ProjectDiBApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectDiBApplication.class, args);
    }

}
