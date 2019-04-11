package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FiGeneratorClientLocal implements FiGeneratorClientInterface {



    @Override
    public void runFiGenerator(MultipartFile fiDoc,String fiDocId, FiGeneratorType fiGeneratorType, String ndId) throws IOException {
        logger.called("runFiGeneratorLocal","type",fiGeneratorType);
        String path= FiProperties.FI_GENERATOR_CLIENT_PATH;
        String fiDocPath=path+ndId+fiDoc.getOriginalFilename();
        Helpers.saveFile(fiDocPath,fiDoc);
        String command = String.format(FiProperties.PYTHON_COMMAND_START+" %s %s %s %s",path+fiGeneratorType+".py",fiDocPath,ndId,fiDocId);
        logger.called("fiGeneratorLocal","command",command);
        Process p = Runtime.getRuntime().exec(command);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        new Thread(() -> {
            try {
                logger.info("fiGeneratorLocal results:"+Helpers.inputStreamToString(p.getInputStream()));
                String errors=Helpers.inputStreamToString(p.getErrorStream());
                if (!errors.isEmpty())
                    logger.error("fiGeneratorLocal results error:"+Helpers.inputStreamToString(p.getErrorStream()));

            } catch (IOException e) {
               logger.error(e);
            }
        }).start();



    }
}
