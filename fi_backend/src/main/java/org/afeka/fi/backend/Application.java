package org.afeka.fi.backend;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.factory.FiFactory;
import org.afeka.fi.backend.factory.NdFactory;
import org.afeka.fi.backend.factory.NdParentFactory;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.repository.RepositoryService;
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

    TreFactory treFactory=new TreFactory();
    NdParentFactory ndParentFactory=new NdParentFactory();
    NdFactory ndFactory=new NdFactory();
    FiFactory fiFactory=new FiFactory();

    public static void main(String[] args) throws IOException {
        FiProperties.init();
        SpringApplication.run(Application.class, args);
    }



    @Bean
    public CommandLineRunner initDemoData() {
        return (args) -> {
            logger.start("initDemoData");
            TRE tre=repositoryService.add(treFactory.newTRE());
            NdParent ndParent= repositoryService.add(ndParentFactory.newNdParent("Root System",tre.ID));
            ND nd=repositoryService.add(ndFactory.newND("UAV SYSTEM",ndParent.ID));
            String fiJson=Helpers.readFile(FiProperties.DATA_PATH+"fiJson");
            repositoryService.add(fiFactory.newFI(Helpers.initGson().fromJson(fiJson,FI.class).PG,nd.ID));
            logger.info("initDemoData insert TRE="+repositoryService.getTre(tre.ID));
            logger.finish("initDemoData");

        };

    }
}