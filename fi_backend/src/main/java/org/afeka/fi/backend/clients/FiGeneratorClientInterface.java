package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.pojo.fiGenerator.FiDocType;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorMode;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FiGeneratorClientInterface{

    FiLogger logger = new FiLogger();

    void runFiGenerator(MultipartFile fiDoc,FiGeneratorType fiGeneratorType,String ndId) throws Exception;

    default void fiDocumentValidator(MultipartFile file) throws FileNotSupportExption{
        logger.called("fiDocumentValidator", "fiDoc", file.getName());
        String docType = Helpers.getFileExtension(file);
        if (!docType.equals(FiDocType.DOC) && !docType.equals(FiDocType.DOCX)) {
            throw new FileNotSupportExption("doc type " + docType + " is not supported docx or doc supported only");
        }
    }

    default void executeFiGenerator(MultipartFile fiDoc,String ndId,FiGeneratorType fiGeneratorType) throws IOException {
        switch (FiProperties.FI_GENERATOR_MODE){
            case FiGeneratorMode.LOCAL:
                new FiGeneratorClientLocal().runFiGenerator(fiDoc,fiGeneratorType,ndId);
                break;
            }
        }

    }

