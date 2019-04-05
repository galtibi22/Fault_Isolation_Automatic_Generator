package org.afeka.fi.backend;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.FiProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class SpringConfig extends FiCommon implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.called("addResourceHandlers","WEBAPP_PATH", FiProperties.WEBAPP_PATH);
        registry
                .addResourceHandler("/app/**")
                .addResourceLocations("file:"+FiProperties.WEBAPP_PATH)
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "app/index.html");

        //registry.addViewController("/").setViewName("forward:/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
    }
}