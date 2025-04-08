package pl.coderslab.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1. Servește din /static/
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");

        // 2. Servește fișiere încărcate (de ex: QuillJS uploads)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}