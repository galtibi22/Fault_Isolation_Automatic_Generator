package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.Generator;
import org.afeka.fi.backend.common.Helpers;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.html.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.commonstructure.NdParent;

import java.io.IOException;

public class NdParentFactory extends ViewFactory<NdParent> {

    /**
     *<ND lbl="UAV SYSTEM"
     * ID="0"
     * typ="0"
     * kIdDsp=""
     * kd="1"
     * nPg="1"
     * doc="SEARCHER_MKII.html"
     * pic="SEARCHER_MKII.png" newV="0" v="-" pdf="" pd="0">
     * @param lbl
     * @param treId
     */
     public NdParent newNdParent(String lbl,String treId){
         view=new NdParent();
         return lbl(lbl).
                 ID(lbl.replaceAll(" ","")+"_"+ Generator.id()).
                 typ("4").
                 kIdDsp("").
                 kd("1").
                 nPg("1").
                 pic("").
                 newV("0").
                 v("-").
                 pdf("").
                 pd("0").
                 doc(view.ID+".html").
                 treId(treId).get();
     }

    private NdParentFactory treId(String treId) {
         view.treId=treId;
         return this;
    }


    private NdParentFactory lbl(String lbl){
        view.lbl=lbl;
          return this;
     }
     private NdParentFactory ID(String ID){
         view.ID=ID;
          return this;
     }
     private NdParentFactory typ(String typ){
         view.typ=typ;
          return this;
     }
     private NdParentFactory kIdDsp(String kIdDsp){
         view.kIdDsp=kIdDsp;
          return this;
     }
     private NdParentFactory kd(String kd){
         view.kd=kd;
          return this;
     }
     private NdParentFactory nPg(String nPg){
         view.nPg=nPg;
          return this;
     }
     private NdParentFactory doc(String doc){
         view.doc=doc;
          return this;
     }
     private NdParentFactory pic(String pic){
         view.pic=pic;
          return this;
     }
     private NdParentFactory newV(String newV){
         view.newV=newV;
          return this;
     }

     private NdParentFactory v(String v){
         view.v=v;
          return this;
     }

     private NdParentFactory pdf(String pdf){
         view.v=pdf;
          return this;
     }
     private NdParentFactory pd(String pd){
         view.v=pd;
          return this;
     }



     @Override
     public void export(String path,NdParent ndParent) throws IOException, DataNotValidException {
         for(ND nd:ndParent.ND){
              new NdFactory().export(path,nd);
         }
         HtmlGenerator htmlGenerator=new HtmlGenerator();
         htmlGenerator.ndParentDoc(ndParent.lbl);
         save(htmlGenerator.toHtml().renderFormatted(),path+ndParent.doc);
     }

   /*  @Override
     public void add(ND nd) throws Exception {
          logger.info("add nd with ID="+nd.ID +" to ndParent with ID="+ndParent.ID);
          String id=nd.ID+"_"+(nd.FI.size()+1);
          ndParent.ND.add(nd);
     }*/

   /*  @Override
     public NdParent get() {
          return ndParent;
     }*/

}









