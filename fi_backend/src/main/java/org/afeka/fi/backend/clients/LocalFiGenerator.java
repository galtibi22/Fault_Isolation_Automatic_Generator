package org.afeka.fi.backend.clients;


import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.FiGenratorException;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@ConfigurationProperties(prefix = "figenerator.local")
@Service("LocalFiGenerator")
public class LocalFiGenerator implements FiGeneratorClient {

    private String path;
    private String pythonprefix;
    @Override
    public void runFiGenerator(MultipartFile fiDoc,String fiDocId, FiGeneratorType fiGeneratorType, String ndId) throws IOException {
        logger.called("runLocalFiGenerator","type",fiGeneratorType);
        Path path= Paths.get(getPath(),fiGeneratorType+".py");
        Path fiDocPath=Files.createTempFile(Helpers.removeSpecialChars(Helpers.getFileSimpleName(fiDoc)),"."+Helpers.getFileExtension(fiDoc));
        fiDocPath.toFile().deleteOnExit();
        Files.write(fiDocPath,fiDoc.getBytes());
        String command = String.format(pythonprefix+" %s %s %s %s",path.toAbsolutePath(),fiDocPath.toAbsolutePath(),ndId,fiDocId);
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPythonprefix() {
        return pythonprefix;
    }

    public void setPythonprefix(String pythonprefix) {
        this.pythonprefix = pythonprefix;
    }
}
