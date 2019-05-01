package org.afeka.fi.tests.units;


import org.afeka.fi.backend.clients.HttpClientImpl;
import org.afeka.fi.backend.common.FiCommonTest;
import org.afeka.fi.backend.common.Helpers;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import org.afeka.fi.backend.pojo.ocr.OcrWebServiceResponse;
import org.springframework.web.multipart.MultipartFile;


public class HttpClientImplTest extends FiCommonTest {

    private HttpClientImpl httpClient=new HttpClientImpl();
    @Test
    public void ocrwebservice_doc_upload() throws Exception {
        String url = "https://www.ocrwebservice.com/restservices/processDocument?language=english&gettext=true&outputformat=doc";
        String userName="galtibi";
        String licenseCode="65D923DA-A426-46DE-AD97-71D73052A0D1";
        byte[] bytes= Files.readAllBytes(Paths.get("/Users/gal.tibi/afekaProjects/Fault_Isolation_Automatic_Generator/data/fi_table_image_excelent.jpg"));
        ArrayList<Header> headers=new ArrayList<>();
        headers.add(new BasicHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((userName + ":" + licenseCode).getBytes())));
        headers.add(new BasicHeader("Content-Type", "application/json"));
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> hostname.equals("69.64.73.46"));
        OcrWebServiceResponse ocrWebServiceResponse=httpClient.post(url,headers.toArray(new BasicHeader[0]),bytes,new OcrWebServiceResponse());
        MultipartFile file=httpClient.getFile(ocrWebServiceResponse.getOutputFileUrl().replaceAll("https","http"),null);
        logger.info("get file from ocr online"+file.getOriginalFilename());
        //logger.info(Helpers.initGson().toJson();

    }


}



