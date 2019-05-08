package org.afeka.fi.backend.services;

import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.factory.NdParentFactory;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NdParentServiceImpl implements EntityService<NdParent> {

    private EntityDao<NdParent> ndParentDao;
    private EntityDao<TRE> treDao;
    private NdParentFactory ndParentFactory;

    @Autowired
    public void init(EntityDao<NdParent>ndParentDao, EntityDao<TRE> treDao,NdParentFactory ndParentFactory){
        this.ndParentDao=ndParentDao;
        this.treDao=treDao;
        this.ndParentFactory=ndParentFactory;
    }


    @Override
    public NdParent add(ViewCreateRequest viewCreateRequest, String parentId) throws ResourceNotFoundException, AddEntityExption {
        treDao.find(parentId);
        return ndParentDao.add(ndParentFactory.newNdParent(viewCreateRequest, parentId));
    }

    @Override
    public NdParent update(NdParent ndParent) throws ResourceNotFoundException {
        return ndParentDao.update(ndParent);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption {
        ndParentDao.delete(id);
    }

    @Override
    public NdParent get(String id) throws ResourceNotFoundException {
        return ndParentDao.get(id);
    }

    @Override
    public NdParent find(String id) throws ResourceNotFoundException {
        return ndParentDao.find(id);
    }

    @Override
    public List<NdParent> getAll(String parentId) throws ResourceNotFoundException {
        return ndParentDao.getAll(parentId);
    }
}
