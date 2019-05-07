package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.repository.NdParentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository

public class NdParentDaoImpl implements EntityDao<NdParent> {
    private FiLogger logger= new FiLogger();
    private EntityDao<ND> ndDao;
    private NdParentRepository ndParentRepository;

    @Autowired
    public void init(NdParentRepository ndParentRepository,EntityDao<ND> ndDao){
        this.ndParentRepository=ndParentRepository;
        this.ndDao=ndDao;
    }
    @Transactional
    @Override
    public void delete(String id) throws DeleteEntityExption, ResourceNotFoundException {
        logger.called("deleteNdParent","id",id);
        NdParent ndParentToDelete=get(id);
        for (ND nd:ndParentToDelete.ND)
            ndDao.delete(nd.ID);
        try{
            ndParentRepository.deleteById(id);}
        catch (EmptyResultDataAccessException e){
            throw new DeleteEntityExption("Cannot delete NdParent with ID"+id+" because "+e.getMessage());
        }
    }

    @Override
    public NdParent find(String id) throws ResourceNotFoundException {
        logger.called("findNdParent","id",id);
        return ndParentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find ndParent with id "+id));

    }

    @Transactional
    @Override
    public NdParent add(NdParent ndParent) {
        logger.called("addNdParent","ndParent",ndParent);
        return ndParentRepository.save(ndParent);
    }

    @Override
    public NdParent get(String id) throws ResourceNotFoundException {
        logger.called("getNdParent","id",id);
        NdParent ndParent=find(id);
        List<ND> nds=ndDao.getAll(ndParent.ID);
        nds.forEach(nd-> {
            try {
                ndParent.ND.add(ndDao.get(nd.ID));
            } catch (ResourceNotFoundException e) {
                logger.error("cannot find ndId "+nd.ID);
            }
        });
        return ndParent;
    }

    @Override
    public List<NdParent> getAll(String parentId) throws ResourceNotFoundException {
        logger.called("getAllNdParents","treId",parentId);
        return ndParentRepository.findAll(Example.of(new NdParent(parentId)));
    }
    @Transactional
    public NdParent update(NdParent ndParent) throws ResourceNotFoundException {
        logger.called("updateNdParent","ndParent",ndParent);
        find(ndParent.ID);
        return ndParentRepository.save(ndParent);
    }
}
