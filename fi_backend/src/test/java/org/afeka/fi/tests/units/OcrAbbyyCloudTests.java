package org.afeka.fi.tests.units;

import org.afeka.fi.backend.clients.OcrAbbyyCloud;
import org.afeka.fi.tests.common.FiCommonTest;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

public class OcrAbbyyCloudTests extends FiCommonTest {

    @Autowired
    public OcrAbbyyCloud ocrAbbyyCloud;

    @Test
    public void imageToDocxWithAbbyyOcr() throws Exception {
        File source=new File("/Users/gal.tibi/afekaProjects/Fault_Isolation_Automatic_Generator/data/NoCommunicationwithLeftWagon.JPG");
        FileInputStream input = new FileInputStream(source);
        MultipartFile multipartFile=new MockMultipartFile("file",
                source.getName(), "text/plain", IOUtils.toByteArray(input));
        multipartFile=ocrAbbyyCloud.run(multipartFile);
        logger.info("ocr with abbyy cloud success. Return file with name "+multipartFile.getName());

    }

}
