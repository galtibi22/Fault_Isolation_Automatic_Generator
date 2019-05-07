package org.afeka.fi.backend.services;

import org.afeka.fi.backend.clients.FiGeneratorClient;
import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.dao.FiDaoImpl;
import org.afeka.fi.backend.dao.NdDaoImpl;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.FileNotSupportExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.FiDoc;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.fiGenerator.FiGeneratorType;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FiService implements FiServiceInterface{

    private FiLogger logger=new FiLogger();
    private EntityDao <ND>ndDao;
    private EntityDao <FI>fiDao;
    private EntityDao<FiDoc> fiDocDao;
    private FiGeneratorClient fiGenerator;

    @Autowired
    public void init(EntityDao<ND> ndDao, EntityDao<FI> fiDao, EntityDao<FiDoc> fiDocDao,FiGeneratorClient fiGenerator) {
        this.ndDao = ndDao;
        this.fiDao = fiDao;
        this.fiGenerator=fiGenerator;
        this.fiDocDao=fiDocDao;
    }

    @Override
    public FI add(ViewCreateRequest viewCreateRequest, String parentId) {
        throw  new NotImplementedException("add Not implement. Use newFi");
    }

    @Override
    public FI update(FI fi) throws ResourceNotFoundException {
        find(fi.ID);
        fiDao.update(fi);
        return get(fi.ID);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption {
        fiDao.delete(id);
    }

    @Override
    public FI get(String id) throws ResourceNotFoundException {
        return fiDao.get(id);
    }

    @Override
    public FI find(String id) throws ResourceNotFoundException {
        return fiDao.find(id);
    }

    @Override
    public List<FI> getAll(String parentId) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void newFi(MultipartFile fiDoc, String ndId, String type) throws Exception {
        ndDao.find(ndId);
        fiGenerator.fiDocumentValidator(fiDoc);
        String fiDocId=fiDocDao.add(new FiDoc(fiDoc)).ID.toString();
        fiGenerator.runFiGenerator(fiDoc,fiDocId, FiGeneratorType.valueOf(type),ndId);
    }

 /*   @Override
    public FiDoc getFiDoc(String fiId) throws ResourceNotFoundException {
        Long fiDocId=get(fiId).fiDocId;
        logger.info("Find fiDocId "+fiDocId+" for FI with id "+fiId);
        return fiDocDao.get(fiDocId.toString());
    }*/
}
