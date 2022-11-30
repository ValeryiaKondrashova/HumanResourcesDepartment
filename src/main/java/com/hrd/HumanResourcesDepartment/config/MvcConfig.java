package com.hrd.HumanResourcesDepartment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/main").setViewName("main");
//        registry.addViewController("/#popup").setViewName("index");

        //registry.addViewController("/login").setViewName("login"); //эта ссылка нужна в index.html, для перехода на авторизацию в login.html
    }

}