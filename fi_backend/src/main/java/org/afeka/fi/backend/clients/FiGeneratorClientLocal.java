package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FiGeneratorClientLocal implements FiGeneratorClientInterface {



    @Override
    public void runFiGenerator(MultipartFile fiDoc, FiGeneratorType fiGeneratorType, String ndId) throws IOException {
        logger.called("runFiGeneratorLocal","type",fiGeneratorType);
        String path= FiProperties.FI_GENERATOR_CLIENT_PATH;
        String fiDocPath=path+ndId+fiDoc.getName();
        Helpers.saveFile(fiDocPath,fiDoc);
        String command = String.format("python %s %s %s",path+fiGeneratorType+".py",fiDocPath,ndId);
        logger.called("fiGeneratorLocal","command",command);
        Process p = Runtime.getRuntime().exec(command);


    }
}
