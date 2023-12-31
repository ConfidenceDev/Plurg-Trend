package me.plurg.plurg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
//@ComponentScan
public class WebConfig implements WebMvcConfigurer{

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry cors) {
                cors.addMapping("/api/v1/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "DELETE")
                        .allowedHeaders("*");

                //.allowedOrigins("https://www.plurg.me", "https://www.plurg-trend.onrender.com")
            }
        };
    }

    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/assets/**",
                        "/css/**",
                        "/js/**",
                        "/**")
                .addResourceLocations(
                        "classpath:/static/assets/",
                        "classpath:/static/css/",
                        "classpath:/static/js/",
                        "classpath:/static/policy.html");
    }*/
}
