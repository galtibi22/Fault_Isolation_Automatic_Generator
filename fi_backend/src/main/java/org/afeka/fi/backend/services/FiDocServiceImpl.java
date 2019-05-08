package org.afeka.fi.backend.services;

import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.FiDoc;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FiDocServiceImpl implements EntityService<FiDoc> {

    private EntityDao<FiDoc> fiDocDao;
    private EntityDao<FI> fiDao;
    @Autowired
    public void init(EntityDao<FiDoc> fiDocDao, EntityDao<FI> fiDao) {
        this.fiDocDao=fiDocDao;
        this.fiDao=fiDao;
    }

    @Override
    public FiDoc add(ViewCreateRequest viewCreateRequest, String parentId) {
        throw new NotImplementedException("add");
    }

    @Override
    public FiDoc update(FiDoc fiDoc) throws ResourceNotFoundException {
        throw new NotImplementedException("update");
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption {
        throw new NotImplementedException("delete");

    }

    @Override
    public FiDoc get(String fiId) throws ResourceNotFoundException {
        String fiDocId=fiDao.get(fiId).fiDocId+"";
        return fiDocDao.get(fiDocId);
    }

    @Override
    public FiDoc find(String id) throws ResourceNotFoundException {
        throw new NotImplementedException("find");
    }

    @Override
    public List<FiDoc> getAll(String parentId) throws ResourceNotFoundException {
        throw new NotImplementedException("getAll");

    }
}
