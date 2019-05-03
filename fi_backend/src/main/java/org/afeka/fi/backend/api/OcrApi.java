package org.afeka.fi.backend.api;

import org.afeka.fi.backend.clients.OcrClient;
import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.FiDoc;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.afeka.fi.backend.pojo.http.GeneralResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;

@RequestMapping(path = "api/fronted")
@RestController

public class OcrApi extends CommonApi {
    @Value("${ocrproviders}")
    private String ocrProviders;
    @PostMapping(value = "/ocr/{ocrProvider}",headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Resource> ocr(HttpServletRequest request, @RequestBody MultipartFile fiImage,@PathVariable String ocrProvider) throws Exception {
        securityCheck(request, Role.user);
        logger.called("ocrApi", "ocrProvider and fiImage",ocrProvider,fiImage.getOriginalFilename());
        ocrClient= (OcrClient) context.getBean(ocrProvider);
        MultipartFile fiDoc=ocrClient.run(fiImage);
        return initMsWordResponse(fiDoc);
       /* return ResponseEntity.ok().header("Content-Disposition", "attachment;filename="+fiDoc.getName())
                .contentType(MediaType.parseMediaType("application/msword"))
                .contentLength(fiDoc.getSize())
                .body(new InputStreamResource(fiDoc.getInputStream()));
*/

    }
    @GetMapping(value = "/ocr",headers = HttpHeaders.AUTHORIZATION)
    public String getOcrProviders(HttpServletRequest request){
        securityCheck(request, Role.user);
        logger.info("getOcrProviders api called");
        logger.info("getOcrProviders api return ocrProviders "+ocrProviders);
        return ocrProviders;

    }
}