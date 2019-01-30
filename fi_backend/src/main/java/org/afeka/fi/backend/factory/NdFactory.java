package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.exception.DataNotValidException;
import org.afeka.fi.backend.html.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.ND;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.*;

public class NdFactory  extends ViewFactory {
     private ND nd=new ND();
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
     public NdFactory(String lbl,String id){
          lbl(lbl).
                  ID(id).
                  typ("4").
                  kIdDsp("").
                  kd("1").
                  nPg("1").
                  pic("").
                  newV("0").
                  v("-").
                  pdf("").
                  pd("60").
                  doc(id+"-chapter.html");
     }

     public NdFactory(ND nd){
          this.nd=nd;
     }
     /**
      * Every time you add new FI you must run generateMdDoc
      */
     public void generateNdDoc() throws DataNotValidException {
          HtmlGenerator htmlGenerator=new HtmlGenerator();
          if (nd.FI.size()==0)
               throw new DataNotValidException("Cannot generate ndDoc for nd.fi.size()=0");
          htmlGenerator.ndDoc(nd.lbl,nd.FI);
          nd.htmlObject=htmlGenerator.toHtml();
     }


     private NdFactory lbl(String lbl){
          nd.lbl=lbl;
          return this;
     }
     private NdFactory ID(String ID){
          nd.ID=ID;
          return this;
     }
     private NdFactory typ(String typ){
          nd.typ=typ;
          return this;
     }
     private NdFactory kIdDsp(String kIdDsp){
          nd.kIdDsp=kIdDsp;
          return this;
     }
     private NdFactory kd(String kd){
          nd.kd=kd;
          return this;
     }
     private NdFactory nPg(String nPg){
          nd.nPg=nPg;
          return this;
     }
     private NdFactory doc(String doc){
          nd.doc=doc;
          return this;
     }
     private NdFactory pic(String pic){
          nd.pic=pic;
          return this;
     }
     private NdFactory newV(String newV){
          nd.newV=newV;
          return this;
     }

     private NdFactory v(String v){
          nd.v=v;
          return this;
     }

     private NdFactory pdf(String pdf){
          nd.v=pdf;
          return this;
     }
     private NdFactory pd(String pd){
          nd.v=pd;
          return this;
     }



     @Override
     public void export(String path) throws IOException {
         for(FI fi:nd.FI){
              new FiFactory(fi).export(path);
         }
         save(nd.htmlObject.renderFormatted(),path+nd.doc);
     }

     @Override
     public void add(Object o) throws Exception {
          String id=nd.ID+"_"+(nd.FI.size()+1);
          FiFactory fiFactory=new FiFactory(o.toString(),id);
          nd.FI.add(fiFactory.fi);
     }

}









