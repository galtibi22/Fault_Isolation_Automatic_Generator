package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FiDoc;
import org.afeka.fi.backend.repository.FIDocRepository;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Repository
public class FiDocDaoImpl implements EntityDao<FiDoc> {
    private FiLogger logger=new FiLogger();
    private FIDocRepository fiDocRepository;
    @Autowired
    public void init(FIDocRepository fiDocRepository){

        this.fiDocRepository=fiDocRepository;
    }
    @Override
    public void delete(String id) throws DeleteEntityExption, ResourceNotFoundException {

    }

    @Override
    public FiDoc find(String id) throws ResourceNotFoundException {
        throw new NotImplementedException("find");
    }
    @Transactional
    @Override
    public FiDoc add(FiDoc fiDoc) {
        logger.called("addFiDoc","fiDoc",fiDoc);
        return fiDocRepository.save(fiDoc);
    }

    @Override
    public FiDoc get(String id) throws ResourceNotFoundException {
        logger.called("getFiDoc","id",id);
        return fiDocRepository.findById(Long.valueOf(id)).orElseThrow(()->new ResourceNotFoundException("Cannot find fiDoc for id "+id));

    }

    @Override
    public List<FiDoc> getAll(String parentId) throws ResourceNotFoundException {
        throw new NotImplementedException("delete");
    }

    @Override
    public FiDoc update(FiDoc fiDoc) throws ResourceNotFoundException {
        throw new NotImplementedException("update");
    }



}
