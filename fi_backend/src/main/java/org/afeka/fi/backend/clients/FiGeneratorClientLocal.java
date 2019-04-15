package org.afeka.fi.backend.clients;


import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.FiGenratorException;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FiGeneratorClientLocal implements FiGeneratorClientInterface {



    @Override
    public void runFiGenerator(MultipartFile fiDoc,String fiDocId, FiGeneratorType fiGeneratorType, String ndId) throws IOException {
        logger.called("runFiGeneratorLocal","type",fiGeneratorType);
        Path path= Paths.get(FiProperties.FI_GENERATOR_CLIENT_PATH,fiGeneratorType+".py");
        //Path fiDocPath=path.relativize(Paths.get(ndId+fiDoc.getOriginalFilename()));
        Path fiDocPath=Files.createTempFile(Helpers.getFileSimpleName(fiDoc),"."+Helpers.getFileExtension(fiDoc));
        fiDocPath.toFile().deleteOnExit();
        Files.write(fiDocPath,fiDoc.getBytes());
       // Files.copy(fiDoc.getInputStream(),fiDocPath);
        String command = String.format(FiProperties.PYTHON_COMMAND_START+" %s %s %s %s",path.toAbsolutePath(),fiDocPath.toAbsolutePath(),ndId,fiDocId);
        logger.called("fiGeneratorLocal","command",command);
        Process p = Runtime.getRuntime().exec(command);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error(e);
        }
        String info=Helpers.inputStreamToString(p.getInputStream());
        if (!info.isEmpty())
            logger.info("fiGeneratorLocal results:"+info);
        String errors=Helpers.inputStreamToString(p.getErrorStream());
        if (!errors.isEmpty()) {
            logger.error("fiGeneratorLocal results error:" + errors);
            throw new FiGenratorException("Fi generator cannot generate FI json. Please check if you select the correct type of fiGenerator for your fiDoc. StackTrace:"+errors);
        }


    }
}
