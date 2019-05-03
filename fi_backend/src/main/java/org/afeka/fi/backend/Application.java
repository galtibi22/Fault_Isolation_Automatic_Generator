package org.afeka.fi.backend;

import org.afeka.fi.backend.common.Constants;
import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.repository.RepositoryService;
import org.afeka.fi.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication(exclude = JmxAutoConfiguration.class)

public class Application extends FiCommon {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    UserRepository userRepository;
    public static void main(String[] args) throws Exception {
        //FiProperties.init();
        SpringApplication.run(Application.class, args);
    }



    @Bean
    public CommandLineRunner initDemoData() {
        return (args) -> {
           loadUsers();
        };

    }

    private void loadUsers() {
        userRepository.save(new User(Constants.ADMIN_USER_NAME ,Constants.ADMIN_PASSWORD, Role.admin,"Admin","Admin"));
        userRepository.save(new User("figenerator",Constants.USER_PASSWORD, Role.generator,"FiGenerator","FiGenerator"));
        userRepository.save(new User("user",Constants.USER_PASSWORD, Role.user,"user","user"));

    }
}