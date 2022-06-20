package com.douzone.jblog.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.douzone.config.web.FileuploadConfig;
import com.douzone.config.web.MVCConfig;
import com.douzone.config.web.MessageConfig;
import com.douzone.config.web.SecurityConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({ "com.douzone.jblog.controller", "com.douzone.mysite.exception" })
@Import({ MVCConfig.class, SecurityConfig.class, MessageConfig.class, FileuploadConfig.class })
public class WebConfig implements WebMvcConfigurer {

}