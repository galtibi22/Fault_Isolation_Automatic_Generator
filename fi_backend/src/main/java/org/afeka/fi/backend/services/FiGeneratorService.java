package org.afeka.fi.backend.services;

import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DataFactoryNotFoundException;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FI;

import java.util.List;

public interface FiGeneratorService {
    public List<FI> newFis (String ndId, Long fiDocId, String json) throws ResourceNotFoundException, DataFactoryNotFoundException, AddEntityExption;
    }
