package org.afeka.fi.backend.services;

import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.dao.EntityDao;
import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DataFactoryNotFoundException;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.factory.FiFactory;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;

@Service
public class FiGeneratorServiceImpl implements FiGeneratorService{

    private EntityDao<ND>  ndDao;
    private EntityDao<FI>  fiDao;
    private FiFactory fiFactory;

    @Autowired
    public void init(EntityDao<ND> ndDao,EntityDao<FI>fiDao,FiFactory fiFactory){
        this.fiDao=fiDao;
        this.ndDao=ndDao;
        this.fiFactory=fiFactory;
    }
    @Override
    public List<FI> newFis (String ndId, Long fiDocId,String json) throws ResourceNotFoundException, DataFactoryNotFoundException, AddEntityExption {
        FI[]fis=new FI[1];
        fis= Helpers.initGson().fromJson(json,fis.getClass());
        ND nd=ndDao.get(ndId);
        for (FI fi:fis){
            fi=fiFactory.newFI(fi,ndId,fiDocId);
            fiDao.add(fi);
        }
        return Arrays.asList(fis);
    }
}
