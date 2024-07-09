package org.zerock.project_dib.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CustomServletConfig implements WebMvcConfigurer {

    // 정적 파일 및 폴더 추가
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:///C:/upload/");


        registry.addResourceHandler("/img/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/img/").addResourceLocations("file:///C:/upload/");


    }

}
