package org.afeka.fi.backend.services;

import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.factory.NdFactory;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NdService implements EntityService<ND> {
    private EntityDao<ND> ndDao;
    private EntityDao<NdParent> ndParentDao;
    private NdFactory ndFactory;

    @Autowired
    public void init(EntityDao<ND> ndDao,EntityDao<NdParent> ndParentDao,NdFactory ndFactory){
        this.ndDao=ndDao;
        this.ndParentDao=ndParentDao;
        this.ndFactory=ndFactory;
    }
    @Override
    public ND add(ViewCreateRequest viewCreateRequest, String parentId) throws ResourceNotFoundException {
        ndParentDao.find(parentId);
        return ndDao.add(ndFactory.newND(viewCreateRequest, parentId));
    }

    @Override
    public ND update(ND nd) throws ResourceNotFoundException {
        return ndDao.update(nd);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption {
        ndDao.delete(id);
    }

    @Override
    public ND get(String id) throws ResourceNotFoundException {
        return ndDao.get(id);
    }

    @Override
    public ND find(String id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<ND> getAll(String parentId) throws ResourceNotFoundException {
        return null;
    }
}
