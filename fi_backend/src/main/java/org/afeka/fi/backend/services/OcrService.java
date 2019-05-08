package org.afeka.fi.backend.services;

import org.springframework.web.multipart.MultipartFile;

public interface OcrService {

    public MultipartFile run(MultipartFile file, String ocrProvider) throws Exception;

    public String getOcrProviders();

}
