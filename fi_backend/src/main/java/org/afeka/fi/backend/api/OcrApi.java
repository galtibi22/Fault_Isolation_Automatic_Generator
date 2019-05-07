package org.afeka.fi.backend.api;

import org.afeka.fi.backend.clients.OcrClient;
import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.pojo.auth.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

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
        return initFileResponse(fiDoc);

    }
    @GetMapping(value = "/ocr",headers = HttpHeaders.AUTHORIZATION)
    public String getOcrProviders(HttpServletRequest request){
        securityCheck(request, Role.user);
        logger.info("getOcrProviders api called");
        logger.info("getOcrProviders api return ocrProviders "+ocrProviders);
        return ocrProviders;

    }
}