package com.seeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//
//// 请求跨域
//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //添加映射路径
//        registry.addMapping("/**")
//                //是否发送Cookie
//                .allowCredentials(true)
//                //设置放行哪些原始域   SpringBoot2.4.4下低版本使用.allowedOrigins("*")
//                .allowedOriginPatterns("*")
//                //放行哪些请求方式
//                .allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE"})
//                //.allowedMethods("*") //或者放行全部
//                //放行哪些原始请求头部信息
//                .allowedHeaders("*")
//                //暴露哪些原始请求头部信息
//                .exposedHeaders("*");
//    }
//}
@Configuration
public class CorsConfig {
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:5173"); // 1 // 8972
        corsConfiguration.addAllowedHeader("*"); // 2
        corsConfiguration.addAllowedMethod("*"); // 3
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}