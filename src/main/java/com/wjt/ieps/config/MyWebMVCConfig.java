package com.wjt.ieps.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {
    @Value("${file.location}") //
            String filelocation;  // 这两个是路径
    @Value("${file.path}") //
            String filepath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //匹配到resourceHandler,将URL映射至location,也就是本地文件夹
        registry.addResourceHandler(filepath).addResourceLocations("file:///" + filelocation);//这里最后一个/不能不写
    }
}