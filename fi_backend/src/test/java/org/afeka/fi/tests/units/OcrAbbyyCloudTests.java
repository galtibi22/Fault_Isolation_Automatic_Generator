package org.afeka.fi.tests.units;

import org.afeka.fi.backend.Application;
import org.afeka.fi.backend.clients.OcrAbbyyCloud;
import org.afeka.fi.backend.common.FiCommonTest;
import org.afeka.fi.backend.common.Helpers;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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
