package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.generator.FieldGenerator;
import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.generator.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import org.afeka.fi.backend.pojo.http.ViewCreateRequest;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class NdFactory extends ViewFactory<ND> {
     /**
      *      * <ND lbl="UAV SYSTEM"
      *      ID="0"
      *      typ="0"
      *      kIdDsp=""
      *      kd="1"
      *      nPg="1"
      *      doc="SEARCHER_MKII.html"
      *      pic="SEARCHER_MKII.png"
      *      newV="0" v="-"
      *      pdf=""
      *      pd="0">
      */
     /**
      *    <ND lbl="LOS GDT"
      *    ID="(IN)SH-06-7IAF_6.5"
      *    typ="4"
      *kIdDsp=""
      * kd="1"
      * nPg="1"
      * doc="(IN)SH-06-7IAF_6.5-chapter.html"
      * pic=""
      * newV="0"
      * v="-"
      * pdf=""
      * pd="45">
      */
     public ND newND(ViewCreateRequest viewCreateRequest, String ndParentId){
         logger.called("newND","ndParentId "+ndParentId+" viewCreateRequest ",viewCreateRequest);
         view=new ND();
         return lbl(viewCreateRequest.getLbl()).
                 des(viewCreateRequest.getDes()).
                 ID(viewCreateRequest.getLbl().replaceAll(" ","")+"_"+ FieldGenerator.id()).
                 ndParentId(ndParentId).
                 typ("4").
                 kIdDsp("").
                 kd("1").
                 nPg("1").
                 pic("").
                 newV("0").
                 v("-").
                 pdf("").
                 pd("60").
                 doc(view.ID+"-chapter.html").des(viewCreateRequest.getDes()).get();
     }



     private NdFactory ndParentId(String ndParentId) {
         view.ndParentId=ndParentId;
        return this;
     }

    private NdFactory des(String des) {
        view.des=des;
        return this;
    }
     private NdFactory lbl(String lbl){
         view.lbl=lbl;
          return this;
     }
     private NdFactory ID(String ID){
         view.ID=ID;
          return this;
     }
     private NdFactory typ(String typ){
         view.typ=typ;
          return this;
     }
     private NdFactory kIdDsp(String kIdDsp){
         view.kIdDsp=kIdDsp;
          return this;
     }
     private NdFactory kd(String kd){
         view.kd=kd;
          return this;
     }
     private NdFactory nPg(String nPg){
         view.nPg=nPg;
          return this;
     }
     private NdFactory doc(String doc){
         view.doc=doc;
          return this;
     }
     private NdFactory pic(String pic){
         view.pic=pic;
          return this;
     }
     private NdFactory newV(String newV){
         view.newV=newV;
          return this;
     }

     private NdFactory v(String v){
         view.v=v;
          return this;
     }

     private NdFactory pdf(String pdf){
         view.v=pdf;
          return this;
     }
     private NdFactory pd(String pd){
         view.v=pd;
          return this;
     }



     @Override
     public void export(Path path, ND nd) throws IOException, DataNotValidException {
         for(FI fi:nd.FI){
              new FiFactory().export(path,fi);
         }
         htmlGenerator=new HtmlGenerator();
         if (nd.FI.size()==0)
             throw new DataNotValidException("Cannot generate ndDoc for nd.fi.size()=0");
         htmlGenerator.ndDoc(nd.lbl,nd.FI,nd.des);
         save(htmlGenerator.toHtml(), Paths.get(path+"/"+nd.doc));
     }
}









