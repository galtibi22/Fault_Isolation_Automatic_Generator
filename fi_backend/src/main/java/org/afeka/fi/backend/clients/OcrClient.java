package org.afeka.fi.backend.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface OcrClient {

    MultipartFile run(MultipartFile file) throws Exception;

}
