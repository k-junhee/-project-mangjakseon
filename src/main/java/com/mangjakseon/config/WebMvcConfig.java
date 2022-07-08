package com.mangjakseon.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

//View 페이지에서 프로필 이미지 파일이 저장된 경로에 접근할 수 있게 해주는 클래스
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("/user/ubuntu/profile_images/")   //이미지 폴더의 경로를 잡아 String 변수에 저장
    private String profileUploadFolder;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);

        registry
                .addResourceHandler("/profile_images/**")
                .addResourceLocations("file:///"+profileUploadFolder)   // 폴더 경로
                .setCachePeriod(60*10*6)    //HTTP 캐시 유효기간 초단위 설정
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
//    ResourceHandler 에 대한 보충 학습자료 링크 : https://ncucu.me/30
}
