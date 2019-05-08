package org.afeka.fi.backend.api;

import org.afeka.fi.backend.clients.OcrClient;
import org.afeka.fi.backend.common.CommonApi;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.auth.Role;
import org.afeka.fi.backend.services.OcrService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OcrService ocrService;

    @Autowired
    public void init(OcrService ocrService){
        this.ocrService=ocrService;
    }
    @PostMapping(value = "/ocr/{ocrProvider}",headers = HttpHeaders.AUTHORIZATION)
    public ResponseEntity<Resource> ocr(HttpServletRequest request, @RequestBody MultipartFile fiImage,@PathVariable String ocrProvider) throws Exception {
        securityCheck(request, Role.user);
        logger.called("ocrApi", "ocrProvider and fiImage",ocrProvider,fiImage.getOriginalFilename());
        MultipartFile fiDoc=ocrService.run(fiImage,ocrProvider);
        return initFileResponse(fiDoc);

    }
    @GetMapping(value = "/ocr",headers = HttpHeaders.AUTHORIZATION)
    public String getOcrProviders(HttpServletRequest request){
        securityCheck(request, Role.user);
        logger.info("getOcrProviders api called");
        String ocrProviders=ocrService.getOcrProviders();
        logger.info("getOCrProviders api return "+ocrProviders);
        return ocrProviders;

    }
}