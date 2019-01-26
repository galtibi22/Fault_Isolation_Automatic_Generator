package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.factory.FiFactory;
import org.junit.jupiter.api.Test;

public class PythonClient extends FiCommon {

    public String runFiGenerator(String sourcePath, String resultPath) throws Exception {
        logger.info(String.format("Start fiGenerator client source=%s result=%s",sourcePath,resultPath));
        String path= FiProperties.FI_GENERATOR_CLIENT_PATH;
        String command = String.format("py %s %s %s",path,sourcePath,resultPath);
        logger.info("Start python word to json process. command="+command);
        Process p = Runtime.getRuntime().exec(command );
        logger.info("docx to json process finish");
        String fiJson= Helpers.inputStreamToString(p.getInputStream());
        String fiError=Helpers.inputStreamToString(p.getErrorStream());
        if(!fiError.isEmpty())
            logger.error(fiError);
        return  fiJson;
    }
}
