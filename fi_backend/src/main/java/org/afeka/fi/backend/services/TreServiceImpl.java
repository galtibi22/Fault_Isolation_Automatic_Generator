package org.afeka.fi.backend.services;

import com.itextpdf.text.DocumentException;
import org.afeka.fi.backend.common.FiLogger;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.dao.*;
import org.afeka.fi.backend.exception.AddEntityExption;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.factory.TreFactory;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class TreServiceImpl implements TreService {
    private FiLogger logger=new FiLogger();
    private EntityDao<NdParent> ndParentDao;
    private EntityDao<ND> ndDao;
    private EntityDao<FI> fiDao;
    private EntityDao<TRE> treDao;
    private TreFactory treFactory;
    private EntityDao<User> userDao;


    @Autowired
    public void init(EntityDao<NdParent> ndParentDao, EntityDao<ND> ndDao, EntityDao<FI> fiDao,EntityDao<User> userDao, EntityDao<TRE> treDao, TreFactory treFactory) {
        this.ndParentDao = ndParentDao;
        this.ndDao = ndDao;
        this.fiDao = fiDao;
        this.treDao = treDao;
        this.treFactory = treFactory;
        this.userDao=userDao;
    }


    @Override
    public TRE add(ViewCreateRequest viewCreateRequest, String parentId) throws AddEntityExption {
        return treDao.add(treFactory.newTRE(parentId,viewCreateRequest));
    }

    @Override
    public TRE update(TRE tre) throws ResourceNotFoundException {
        return treDao.update(tre);
    }

    @Override
    public void delete(String id) throws ResourceNotFoundException, DeleteEntityExption {
        treDao.delete(id);
    }

    @Override
    public TRE get(String id) throws ResourceNotFoundException {
        return treDao.get(id);
    }

    @Override
    public TRE find(String id) throws ResourceNotFoundException {
        return treDao.find(id);

    }

    @Override
    public List<TRE> getAll(String parentId) throws ResourceNotFoundException {

        return treDao.getAll(parentId);
    }

    private TRE initTreToExport(TRE tre,String type) throws ResourceNotFoundException {
        logger.called("initTreToExport","tre",tre);
        TRE treToExport=find(tre.ID);
        for(NdParent ndParent:tre.ndParents){
            NdParent ndParentToExport= ndParentDao.find(ndParent.ID);
            treToExport.ndParents.add(ndParentToExport);
            for(ND nd:ndParent.ND){
                ND ndToExport= ndDao.find(nd.ID);
                ndParentToExport.ND.add(ndToExport);
                for (FI fi:nd.FI){
                    FI fiToExport=fiDao.get(fi.ID);
                    if (type.equals("export"))
                        if (fiToExport.status.equals(Status.failed))
                            throw new RuntimeException("Cannot export fi "+fiToExport.ID+" with status failed. Please fix the fi or select other fis to export");
                    fiToExport.PG.remove(0);
                    ndToExport.FI.add(fiToExport);
                }
            }
        }
        return treToExport;
    }

    public Path export(TRE tre) throws ResourceNotFoundException, IOException, JAXBException, DataNotValidException {
        TRE treToExport=initTreToExport(tre,"export");
        TreFactory treFactory=new TreFactory();
        Path resultPath= Files.createTempDirectory("results").toAbsolutePath();
        treFactory.export(resultPath,treToExport);
        Path zipPath=Files.createTempFile("export",".zip");
        Helpers.zip(resultPath,zipPath);
        return zipPath;
    }

    public Path exportReport(TRE tre) throws ResourceNotFoundException, IOException, DocumentException {
        TRE treToExport=initTreToExport(tre,"report");
        TreFactory treFactory=new TreFactory();
        Path reportPath=Files.createTempFile(tre.ID+"_report_",".pdf");
        treFactory.exportReport(reportPath,treToExport);
        return reportPath;
    }
}
