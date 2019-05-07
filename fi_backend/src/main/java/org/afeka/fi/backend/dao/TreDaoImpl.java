package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.dao.NdParentDaoImpl;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.repository.TreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class TreDaoImpl implements EntityDao<TRE> {

    FiLogger logger= new FiLogger();

    private TreRepository treRepository;
    private EntityDao<NdParent> ndParentDao;

    @Autowired
    public void init(TreRepository treRepository, EntityDao<NdParent> ndParentDao){
        this.treRepository=treRepository;
        this.ndParentDao=ndParentDao;
    }

    @Transactional
    @Override
    public void delete(String id) throws DeleteEntityExption, ResourceNotFoundException {
        logger.called("deleteTre","id",id);
        TRE treToDelete=get(id);
        for (NdParent ndParent:treToDelete.ndParents)
            ndParentDao.delete(ndParent.ID);
        try{
            treRepository.deleteById(id);}
        catch (EmptyResultDataAccessException e){
            throw new DeleteEntityExption("Cannot delete TRE with ID"+id+" because "+e.getMessage());
        }

    }

    @Override
    public List<TRE> getAll(String userName) throws ResourceNotFoundException {
        logger.called("getAllTres","userId",userName);
        List<TRE> tres=treRepository.findAll(Example.of(new TRE(userName)));
        for (TRE tre:tres){
            tre=get(tre.ID);
        }
        return tres;
    }

    @Override
    public TRE find(String id) throws ResourceNotFoundException {
        logger.called("findTre","id",id);
        return treRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find tre with id "+id));
    }

    @Transactional
    @Override
    public TRE add(TRE tre) {
        logger.called("addTre","tre",tre);
        return treRepository.save(tre);
    }

    @Override
    public TRE get(String id) throws ResourceNotFoundException {
        logger.called("getTre","id",id);
        TRE tre=find(id);
        List<NdParent> ndParents=ndParentDao.getAll(tre.ID);
        ndParents.forEach(ndParent-> {
            try {
                tre.ndParents.add(ndParentDao.get(ndParent.ID));
            } catch (ResourceNotFoundException e) {
                logger.error("cannot find ndParentId "+ndParent.ID);
            }
        });
        return tre;
    }
    @Transactional
    public TRE update(TRE tre) throws ResourceNotFoundException {
        logger.called("updateTre","tre",tre);
        find(tre.ID);
        return treRepository.save(tre);
    }
}
