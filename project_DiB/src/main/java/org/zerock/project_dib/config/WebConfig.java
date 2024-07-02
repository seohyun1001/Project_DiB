package org.zerock.project_dib.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/js/");
        registry.addResourceHandler("/fonts/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/fonts/");
        registry.addResourceHandler("/css/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/assets/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/img/**").addResourceLocations("/resources/").addResourceLocations("classpath:/static/img/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:///C:/img/");
    }
}
