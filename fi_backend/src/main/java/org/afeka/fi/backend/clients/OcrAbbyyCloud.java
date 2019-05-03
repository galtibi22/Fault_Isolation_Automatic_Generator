package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.ocr.AbbyyOcrResponse;
import org.afeka.fi.backend.pojo.ocr.OcrWebServiceResponse;
import org.afeka.fi.backend.pojo.ocr.Response;
import org.apache.http.Header;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
@Service("OcrAbbyyCloud")
@ConfigurationProperties(prefix = "ocrprovider.ocrabbyycloud")
public class OcrAbbyyCloud extends FiCommon implements OcrClient {
    //
    private String password;
    private String applicationId;

    @Override
    public MultipartFile run(MultipartFile file) throws Exception {
        logger.called("OcrAbbyyCloud.run","fiImage",file.getOriginalFilename());
        HttpClientImpl httpClient=new HttpClientImpl();
        String url = "http://cloud-eu.ocrsdk.com/processImage?language=english&exportFormat=docx";
        Header[] headers={
                new BasicHeader(HttpHeaders.AUTHORIZATION, Helpers.encodeBasicAuth(applicationId,password))
                ,new BasicHeader(HttpHeaders.CONTENT_TYPE,"application/octet-stream")};
        Response abbyyOcrResponse=httpClient.post(url,headers,file.getBytes(),new Response());
        String status=abbyyOcrResponse.getTask().status;
        while(status.equals("Submitted") || status.equals("Queued") || status.equals("InProgress")){
            Thread.sleep(2000);
            abbyyOcrResponse=httpClient.get("http://cloud-eu.ocrsdk.com/getTaskStatus?taskId="+abbyyOcrResponse.getTask().id,headers,new Response());
            status=abbyyOcrResponse.getTask().status;
        }
        logger.info("The ocr process for doc with ID="+abbyyOcrResponse.getTask().id);
        return httpClient.getFile(abbyyOcrResponse.getTask().resultUrl,null);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
