package org.afeka.fi.backend;

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
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@SpringBootApplication

public class Application extends FiCommon {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    UserRepository userRepository;
  /*
    TreFactory treFactory=new TreFactory();
    NdParentFactory ndParentFactory=new NdParentFactory();
    NdFactory ndFactory=new NdFactory();
    FiFactory fiFactory=new FiFactory();
*/
    public static void main(String[] args) throws Exception {
        FiProperties.init();
        SpringApplication.run(Application.class, args);
    }



    @Bean
    public CommandLineRunner initDemoData() {
        return (args) -> {
           loadUsers();
            /* logger.start("initDemoData");
            TRE tre=repositoryService.add(treFactory.newTRE("gal@gal.com"));
            NdParent ndParent= repositoryService.add(ndParentFactory.newNdParent("Root System",tre.ID));
            ND nd=repositoryService.add(ndFactory.newND("UAV SYSTEM",ndParent.ID));
            String fiJson=Helpers.readFile(FiProperties.DATA_PATH+"fiJson");
            repositoryService.add(fiFactory.newFI(Helpers.initGson().fromJson(fiJson,FI.class).PG,nd.ID));
            logger.info("initDemoData insert TRE="+repositoryService.getTre(tre.ID));
            logger.finish("initDemoData");
*/
        };

    }

    private void loadUsers() {
        userRepository.save(new User("admin","Aa123456", Role.admin,"Admin","Admin"));
        userRepository.save(new User("figenerator","Aa123456", Role.generator,"FiGenerator","FiGenerator"));
    }
}