package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.pojo.fiGenerator.FiDocType;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorMode;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FiGeneratorClientInterface{

    FiLogger logger = new FiLogger();

    void runFiGenerator(MultipartFile fiDoc,String type,String ndId) throws IOException;

    default void fiDocumentValidator(MultipartFile file) throws FileNotSupportExption {
        logger.called("fiDocumentValidator","fiDoc",file.getName());
        String type= Helpers.getFileExtension(file);
        if (!type.equals(FiDocType.DOC) || !type.equals(FiDocType.DOCX)){
            throw new FileNotSupportExption("file type "+type+" is not supported"+" docx or doc supported only");
        }
    }

    default void executeFiGenerator(MultipartFile fiDoc,String ndId) throws IOException {
        switch (FiProperties.FI_GENERATOR_MODE){
            case FiGeneratorMode.LOCAL:
                new FiGeneratorClientLocal().runFiGenerator(fiDoc,fiDoc.getName(),ndId);
                break;
            }
        }

    }

