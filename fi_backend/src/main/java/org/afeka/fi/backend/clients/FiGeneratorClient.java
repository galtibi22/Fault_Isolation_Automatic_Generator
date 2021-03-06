package org.afeka.fi.backend.clients;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.pojo.fiGenerator.FiDocType;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FiGeneratorClient {

    FiLogger logger = new FiLogger();

    void runFiGenerator(MultipartFile fiDoc,String fiDocId,FiGeneratorType fiGeneratorType,String ndId) throws Exception;

    default void fiDocumentValidator(MultipartFile file) throws FileNotSupportExption{
        logger.called("fiDocumentValidator", "fiDoc", file.getName());
        String docType = Helpers.getFileExtension(file);
        if (!docType.equals(FiDocType.DOC) && !docType.equals(FiDocType.DOCX) && !docType.equals(FiDocType.PDF)) {
            throw new FileNotSupportExption("doc type " + docType + " is not supported docx, doc or pdf supported only");
        }
    }

    }

