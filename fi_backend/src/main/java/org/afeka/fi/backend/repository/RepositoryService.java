package org.afeka.fi.backend.repository;

import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DeleteEntityExption;
import org.afeka.fi.backend.exception.ResourceNotFoundException;
import org.afeka.fi.backend.pojo.auth.User;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class RepositoryService extends FiCommon {

    @Autowired
    FIRepository fiRepository;
    @Autowired
    NdRepository ndRepository;
    @Autowired
    NdParentRepository ndParentRepository;
    @Autowired
    TreRepository treRepository;

    @Autowired
    FIDocRepository fiDocRepository;

 /*   @Transactional
    public TRE save(TRE tre) {
        tre.ndParents.forEach(ndParent -> save(ndParent));
        return treRepository.save(tre);
    }
    @Transactional
    public NdParent save(NdParent ndParent) {
        ndParent.ND.forEach(nd -> save(nd));
        return ndParentRepository.save(ndParent);
    }
    @Transactional
    public ND save(ND nd){
            nd.FI.forEach(fi-> fiRepository.save(fi));
            return ndRepository.save(nd);
    }*/

    @Transactional
    public FI add(FI fi){
        logger.called("add","fi",fi);
        return fiRepository.save(fi);
    }
    @Transactional
    public ND add(ND nd){
        logger.called("add","nd",nd);
        return ndRepository.save(nd);
    }
    @Transactional
    public NdParent add(NdParent ndParent){
        logger.called("add","ndParent",ndParent);
        return ndParentRepository.save(ndParent);
    }

    @Transactional
    public TRE add(TRE tre){
        logger.called("add","tre",tre);
        return treRepository.save(tre);
    }

    /**
     * Return TRE object with all NdParent childes
     * @param id
     * @return
     */
    public TRE getTre(String id) throws ResourceNotFoundException {
        logger.called("getTre","id",id);
        TRE tre=findTre(id);
        List<NdParent> ndParents=ndParentRepository.findAll(Example.of(new NdParent(tre.ID)));
        ndParents.forEach(ndParent-> {
            try {
                tre.ndParents.add(getNdParent(ndParent.ID));
            } catch (ResourceNotFoundException e) {
                    logger.error("cannot find ndParentId "+ndParent.ID);
            }
        });
        return tre;
    }

    /**
     * Return NdParent object with all ND childes
     * @param id
     * @return
     */
    public NdParent getNdParent(String id) throws ResourceNotFoundException {
        logger.called("getNdParent","id",id);
        NdParent ndParent=findNdParent(id);
        List<ND> nds=ndRepository.findAll(Example.of(new ND(ndParent.ID)));
        nds.forEach(nd-> {
            try {
                ndParent.ND.add(getNd(nd.ID));
            } catch (ResourceNotFoundException e) {
                logger.error("cannot find ndId "+nd.ID);
            }
        });
        return ndParent;
    }

    /**
     * Return ND object with all FI childes
     * @param id
     * @return
     */
    public ND getNd(String id) throws ResourceNotFoundException {
        logger.called("getNd","id",id);
        ND nd=findNd(id);
        List<FI> fis=fiRepository.findAll(Example.of(new FI(nd.ID)));
        fis.forEach(fi1->{
            try {
                nd.FI.add(getFi(fi1.ID));
            } catch (ResourceNotFoundException e) {
                logger.error(e);
            }

        });

        return nd;
    }
    /**
     * Return FI object with all PD childes
     * @param id
     * @return
     */
    public FI getFi(String id) throws ResourceNotFoundException {
        logger.called("getFi", "id", id);
        FI fi = findFI(id);
        fi=Helpers.initGson().fromJson(fi.fiJson, FI.class);
        logger.debug("get fi from db " + fi.fiJson);
        if (fi.PG != null) {
            FI finalFi = fi;
            fi.PG.forEach(pg -> finalFi.pgBounderies.add(new PgBoundery(pg)));
        }
       // fi.PG = null;
        return fi;
    }


    public FI findFI(String id) throws ResourceNotFoundException {
        logger.called("findFI","id",id);
        return fiRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find fi with id "+id));
    }

    public ND findNd(String id) throws ResourceNotFoundException {
        logger.called("findNd","id",id);
            return ndRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find nd with id "+id));
    }
    public NdParent findNdParent(String id) throws ResourceNotFoundException {
        logger.called("findNdParent","id",id);
        return ndParentRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find ndParent with id "+id));

    }

    public TRE findTre(String id) throws ResourceNotFoundException {
        logger.called("findTre","id",id);
        return treRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find tre with id "+id));
    }

    public List<TRE> getTres(User user) throws ResourceNotFoundException {
        logger.called("getTres","userId",user.userName);
        List<TRE> tres=treRepository.findAll(Example.of(new TRE(user.userName)));
        for (TRE tre:tres){
            tre=getTre(tre.ID);
        }
        return tres;
    }
    @Transactional
    public void deleteFi(String id) throws ResourceNotFoundException, DeleteEntityExption {
        FI fiToDelete=findFI(id);
        logger.called("deleteFi","id",id);
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
    @Transactional
    public void deleteNd(String id) throws ResourceNotFoundException, DeleteEntityExption {
        logger.called("deleteNd","id",id);
        ND ndToDelete=getNd(id);
        for (FI fi:ndToDelete.FI)
            deleteFi(fi.ID);
       try{
        ndRepository.deleteById(id);}
        catch (EmptyResultDataAccessException e){
            throw new DeleteEntityExption("Cannot delete ND with ID"+id+" because "+e.getMessage());
        }
    }

    @Transactional
    public void deleteNdParent(String id) throws DeleteEntityExption, ResourceNotFoundException {
        logger.called("deleteNdParent","id",id);
        NdParent ndParentToDelete=getNdParent(id);
        for (ND nd:ndParentToDelete.ND)
            deleteNd(nd.ID);
        try{
        ndParentRepository.deleteById(id);}
         catch (EmptyResultDataAccessException e){
            throw new DeleteEntityExption("Cannot delete NdParent with ID"+id+" because "+e.getMessage());
        }    }


        @Transactional
    public void deleteTre(String id,User user) throws DeleteEntityExption, ResourceNotFoundException {
        logger.called("deleteNdParent","id",id);
        TRE treToDelete=getTre(id);
        for (NdParent ndParent:treToDelete.ndParents)
            deleteNdParent(ndParent.ID);
       try{
        treRepository.deleteById(id);}
       catch (EmptyResultDataAccessException e){
           throw new DeleteEntityExption("Cannot delete TRE with ID"+id+" because "+e.getMessage());
       }
        }

    @Transactional
    public FI updateFi(FI fi) throws ResourceNotFoundException {
        logger.called("updateFi","fi",fi);
        fi.fiJson=Helpers.initGson().toJson(fi);
        return fiRepository.save(fi);
    }

    @Transactional
    public ND updateND(ND nd) throws ResourceNotFoundException {
        logger.called("updateND","nd",nd);
        return ndRepository.save(nd);
    }
    @Transactional
    public NdParent updateNdParent(NdParent ndParent) throws ResourceNotFoundException {
        logger.called("updateNdParent","ndParent",ndParent);
        return ndParentRepository.save(ndParent);
    }
    @Transactional
    public TRE updateTre(TRE tre) throws ResourceNotFoundException {
        logger.called("updateTre","tre",tre);
        return treRepository.save(tre);
    }
    @Transactional
    public String add(MultipartFile doc) throws IOException {
        logger.called("save","fiDoc",doc.getOriginalFilename());
        FiDoc fiDoc=new FiDoc(doc);
        return fiDocRepository.save(fiDoc).ID.toString();
    }

    public FiDoc getFiDoc(Long id) throws ResourceNotFoundException {
        logger.called("getFiDoc","id",id);
        return fiDocRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Cannot find fiDoc for id "+id));
    }
}



