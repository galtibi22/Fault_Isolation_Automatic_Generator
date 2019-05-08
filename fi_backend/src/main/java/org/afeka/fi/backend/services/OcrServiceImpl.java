package org.afeka.fi.backend.services;

import org.afeka.fi.backend.clients.OcrAbbyyCloud;
import org.afeka.fi.backend.clients.OcrClient;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.generator.FieldGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OcrServiceImpl implements OcrService{
    @Value("${ocrproviders}")
    private String ocrProviders;
    private ApplicationContext context;
    private OcrClient ocrClient=new OcrAbbyyCloud();
    @Autowired
    public void init (ApplicationContext context){
        this.context=context;
    }

    @Override
    public MultipartFile run(MultipartFile file,String ocrProvider) throws Exception {
        List<MultipartFile> fiDocs=new ArrayList<>();
        ocrClient= (OcrClient) context.getBean(ocrProvider);
        if(Helpers.getFileExtension(file).equals("zip")){
            return handleZipFile(file);
        }else
            return ocrClient.run(file);

    }

    private MultipartFile handleZipFile(MultipartFile file) throws IOException {
        try {
            Path zip = Files.createTempFile("archive", ".zip");
            Path results = Files.createTempDirectory("results");
            file.transferTo(zip);
            Path imagesUnzipPath = Helpers.unzip(zip);
            File file1=imagesUnzipPath.toFile();
            List<File> images = Arrays.asList(file1.listFiles());
            images.forEach(image -> {
                try {
                    MultipartFile fiDoc = ocrClient.run(new MockMultipartFile(image.getName(), new FileInputStream(image)));
                    Files.write(results.resolve(Helpers.getFileSimpleName(image.getName())+".doc"), fiDoc.getBytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            Path resultZip = Files.createTempFile("resultZip", ".zip");
            Helpers.zip(results, resultZip);
            return new MockMultipartFile(resultZip.getFileName().toString(), new FileInputStream(resultZip.toFile()));
        }catch (Exception e){
            e.printStackTrace();
        }return null;
    }

    @Override
    public String getOcrProviders() {
        return ocrProviders;
    }

}
