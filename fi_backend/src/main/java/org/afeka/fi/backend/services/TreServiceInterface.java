package org.afeka.fi.backend.services;

import com.itextpdf.text.DocumentException;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.TRE;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Path;

public interface TreServiceInterface extends EntityService<TRE> {
    public Path export(TRE tre) throws ResourceNotFoundException, IOException, JAXBException, DataNotValidException;
    public Path exportReport(TRE tre) throws ResourceNotFoundException, IOException, DocumentException;
    }
