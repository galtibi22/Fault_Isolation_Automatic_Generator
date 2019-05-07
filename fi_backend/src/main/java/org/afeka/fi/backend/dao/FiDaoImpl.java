package org.afeka.fi.backend.dao;

import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.FiDoc;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.http.PgBoundery;
import org.afeka.fi.backend.repository.FIDocRepository;
import org.afeka.fi.backend.repository.FIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Repository
public class FiDaoImpl implements EntityDao<FI> {
    private FIRepository fiRepository;
    private FIDocRepository fiDocRepository;
    @Autowired
    public void init(FIRepository fiRepository,FIDocRepository fiDocRepository){
        this.fiDocRepository=fiDocRepository;
        this.fiRepository=fiRepository;
    }
    private FiLogger logger=new FiLogger();
    @Override
    public void delete(String id) throws DeleteEntityExption, ResourceNotFoundException {
        logger.called("deleteFi","id",id);
        FI fiToDelete=find(id);
        try {
            fiRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new DeleteEntityExption("Cannot delete fi with ID"+id+" because "+e.getMessage());
        }
        if (fiRepository.findAll(Example.of(new FI(fiToDelete.ndId,fiToDelete.fiDocId))).size()==0) {
            logger.info("fiDoc with id "+fiToDelete.fiDocId+" not connect to any more FI in ND "+fiToDelete.ndId+". Delete fiDoc");
            fiDocRepository.deleteById(fiToDelete.fiDocId);
        }

    }

    @Override
    public FI find(String id) throws ResourceNotFoundException {
        logger.called("findFi","id",id);
        return fiRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find fi with id "+id));
    }

    @Transactional
    @Override
    public FI add(FI fi) {
        logger.called("add","fi",fi);
        return fiRepository.save(fi);
    }

    @Override
    public FI get(String id) throws ResourceNotFoundException {
        logger.called("getFi", "id", id);
        FI fi = find(id);
        fi= Helpers.initGson().fromJson(fi.fiJson, FI.class);
        logger.debug("get fi from db " + fi.fiJson);
        if (fi.PG != null) {
            FI finalFi = fi;
            fi.PG.forEach(pg -> finalFi.pgBounderies.add(new PgBoundery(pg)));
        }
        return fi;
    }

    @Override
    public List<FI> getAll(String parentId) throws ResourceNotFoundException {
        logger.called("getAllFis","ndId",parentId);
        return fiRepository.findAll(Example.of(new FI(parentId)));
    }

    @Transactional
    public FI update(FI fi) throws ResourceNotFoundException {
        logger.called("updateFi","fi",fi);
        fi.fiJson=Helpers.initGson().toJson(fi);
        return fiRepository.save(fi);
    }

}
