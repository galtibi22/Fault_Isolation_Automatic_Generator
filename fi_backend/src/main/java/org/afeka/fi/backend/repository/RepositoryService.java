package org.afeka.fi.backend.repository;

import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepositoryService {

    @Autowired
    FIRepository fiRepository;
    @Autowired
    NdRepository ndRepository;
    @Autowired
    NdParentRepository ndParentRepository;
    @Autowired
    TreRepository treRepository;


    public TRE save(TRE tre) {
        tre.ndParents.forEach(ndParent -> save(ndParent));
        return treRepository.save(tre);
    }

    public NdParent save(NdParent ndParent) {
        ndParent.ND.forEach(nd -> save(nd));
        return ndParentRepository.save(ndParent);
    }

    public ND save(ND nd){
            nd.FI.forEach(fi->fiRepository.save(fi));
            return ndRepository.save(nd);
    }

    public FI add(FI fi){
        return fiRepository.save(fi);
    }

    public ND add(ND nd){
        return ndRepository.save(nd);
    }

    public NdParent add(NdParent ndParent){
        return ndParentRepository.save(ndParent);
    }

    public TRE add(TRE tre){
        return treRepository.save(tre);
    }

    /**
     * Return TRE object with all NdParent childes
     * @param id
     * @return
     */
    public TRE getTre(String id) {
        TRE tre=treRepository.findById(id).get();
        List<NdParent> ndParents=ndParentRepository.findAll(Example.of(new NdParent(tre.ID)));
        ndParents.forEach(ndParent->tre.ndParents.add(getNdParent(ndParent.ID)));
        return tre;
    }

    /**
     * Return NdParent object with all ND childes
     * @param id
     * @return
     */
    public NdParent getNdParent(String id) {
        NdParent ndParent=ndParentRepository.findById(id).get();
        List<ND> nds=ndRepository.findAll(Example.of(new ND(ndParent.ID)));
        nds.forEach(nd->ndParent.ND.add(getNd(nd.ID)));
        return ndParent;
    }

    /**
     * Return ND object with all FI childes
     * @param id
     * @return
     */
    public ND getNd(String id) {
        ND nd=ndRepository.findById(id).get();
        List<FI> fis=fiRepository.findAll(Example.of(new FI(nd.ID)));
        fis.forEach(fi1->nd.FI.add(Helpers.initGson().fromJson(fi1.fiJson, FI.class)));
        return nd;
    }


}



