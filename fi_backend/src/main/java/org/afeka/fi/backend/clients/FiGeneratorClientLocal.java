package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FiGeneratorClientLocal implements FiGeneratorClientInterface {



    @Override
    public void runFiGenerator(MultipartFile fiDoc, String type,String ndId) throws IOException {
        logger.called("runFiGeneratorLocal","type",type);
        String path= FiProperties.FI_GENERATOR_CLIENT_PATH;
        String fiDocPath=path+ndId+fiDoc.getName();
        Helpers.saveFile(fiDocPath,fiDoc);
        String command = String.format("py %s %s %s",path+type+".py",fiDocPath,ndId);
        logger.called("fiGeneratorLocal","command",command);
        Process p = Runtime.getRuntime().exec(command);


    }
}
