package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.repository.NdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class NdDaoImpl implements EntityDao<ND> {
   private FiLogger logger=new FiLogger();
    private EntityDao<FI> fiDao;
    private NdRepository ndRepository;
    @Autowired
    public void init(NdRepository ndRepository,EntityDao<FI> fiDao){
        this.fiDao=fiDao;
        this.ndRepository=ndRepository;
    }

    @Override
    public void delete(String id) throws DeleteEntityExption, ResourceNotFoundException {
        logger.called("deleteNd","id",id);
        ND ndToDelete=get(id);
        for (FI fi:ndToDelete.FI)
            fiDao.delete(fi.ID);
        try{
            ndRepository.deleteById(id);}
        catch (EmptyResultDataAccessException e){
            throw new DeleteEntityExption("Cannot delete ND with ID"+id+" because "+e.getMessage());
        }
    }

    @Override
    public ND find(String id) throws ResourceNotFoundException {
        logger.called("findNd","id",id);
        return ndRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find nd with id "+id));
    }

    @Transactional
    @Override
    public ND add(ND nd) {
        logger.called("addNd","nd",nd);
        return ndRepository.save(nd);
    }

    @Override
    public ND get(String id) throws ResourceNotFoundException {
        logger.called("getNd","id",id);
        ND nd=find(id);
        List<FI> fis=fiDao.getAll(nd.ID);
        fis.forEach(fi1->{
            try {
                nd.FI.add(fiDao.get(fi1.ID));
            } catch (ResourceNotFoundException e) {
                logger.error(e);
            }

        });

        return nd;
    }

    @Override
    public List<ND> getAll(String parentId) throws ResourceNotFoundException {
        logger.called("getAllNds","ndParentId",parentId);
        return ndRepository.findAll(Example.of(new ND(parentId)));
    }
    @Transactional
    public ND update(ND nd) throws ResourceNotFoundException {
        logger.called("updateND","nd",nd);
        find(nd.ID);
        return ndRepository.save(nd);
    }
}
