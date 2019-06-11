package org.afeka.fi.backend;

import org.afeka.fi.backend.common.FiCommon;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.nio.file.Paths;
@Controller
@Configuration
@EnableWebMvc
@ConfigurationProperties(prefix = "springconfig")

public class SpringConfig extends FiCommon implements WebMvcConfigurer {

    private String ficlientpath;

/*    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.called("addResourceHandlers","ficlientpath", Paths.get(ficlientpath).toAbsolutePath());
        registry
                .addResourceHandler("/app/**")
                .addResourceLocations("file:"+ Paths.get(ficlientpath))
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.called("addResourceHandlers","ficlientpath", Paths.get(ficlientpath).toAbsolutePath());
        registry
                .addResourceHandler("/**")
                .addResourceLocations("file:"+ ficlientpath)
               // .addResourceLocations("file:"+ "C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator_2\\fi_backend\\src\\main\\resources\\static\\")

                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        logger.called("addViewControllers","url", "/index.html");
        registry.addRedirectViewController("/", "/index.html");
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
    }


    public String getFiclientpath() {
        return ficlientpath;
    }

    public void setFiclientpath(String ficlientpath) {
        this.ficlientpath = ficlientpath;
    }
}