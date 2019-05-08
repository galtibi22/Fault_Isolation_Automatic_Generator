package org.afeka.fi.backend.services;

import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.FiDoc;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FiService extends EntityService<FI> {

    public void newFi (MultipartFile fiDoc,String ndId,  String type) throws Exception;

  //  FiDoc getFiDoc(String fiId) throws ResourceNotFoundException;
}
