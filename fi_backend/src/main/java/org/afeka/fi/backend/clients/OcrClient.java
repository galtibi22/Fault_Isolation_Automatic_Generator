package org.afeka.fi.backend.clients;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OcrClient {

    MultipartFile run(MultipartFile file) throws Exception;
}
