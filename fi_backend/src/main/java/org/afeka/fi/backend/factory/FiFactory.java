package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.*;
import org.afeka.fi.backend.exception.DataFactoryNotFoundException;
import org.afeka.fi.backend.html.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FiFactory extends ViewFactory<FI> {
    //private FI fi;

 /*   public FiFactory(String fiJson,String id) throws DataNotFoundException {
        this(Helpers.initGson().fromJson(fiJson,FI.class),id);
    }*/

  /*  public FiFactory(FI fi){

        this.fi=fi;
    }
*/
   /* public FiFactory(FI fi,String id) throws DataNotFoundException {

    }
*/
/*    public FiFactory(FI fi,String id,String ndId) throws DataNotFoundException {
        this(fi,id);
        this.fi.ndId=ndId;
    }*/

 /*   public void newFI(String fiJson,String id,ND nd){

    }*/

    public FI newFI(List<PG> pgs,String ndId) throws DataFactoryNotFoundException {
        view=new FI();
        logger.called("newFI","ndId "+ndId+" pgs ",pgs);
        return findFiLbl(pgs.get(0).htmlObj).
                ID(view.lbl.replaceAll("\"[\\\\-\\\\+\\\\.\\\\^:,]\",\"\"","").replaceAll(" ","")+"_"+ Generator.id()).
                type("10").kd("0").
                doc(pgs.get(0).htmlObj).
                pgs(pgs.subList(1,pgs.size())).
                newV("0").
                v("-").
                pd("60").
                nPg(pgs.size()+"").
                ndId(ndId).
                fiJson().
                get();
    }

    private FiFactory ndId(String ndId) {
        view.ndId=ndId;
        return this;
    }

    private FiFactory fiJson() {
        view.fiJson=Helpers.initGson().toJson(view);
        return this;
    }



    @Override
    public void export(String path,FI fi) throws IOException {
        save(fi.htmlObject.renderFormatted(),path+fi.doc);
        for (PG pg:fi.PG){
            if (pg.htmlObj.htmlObject!=null) {
                logger.info("export pg html with name="+pg.doc);
                save(pg.htmlObj.htmlObject.renderFormatted(),path+pg.doc);
            }
        }

    }

 /*   @Override
    public void add(PG pg) throws Exception {
        throw new Exception("Not implement yet");
    }*/



    private FiFactory pgs(List<PG> pgs) {
        view.PG= pgs;
        for (int i=0;i<view.PG.size();i++){
            HtmlGenerator htmlGenerator=new HtmlGenerator();
            for(HtmlData htmlData:view.PG.get(i).htmlObj.getHtmlData()){
                logger.debug("generate htmlObject for htmlData "+htmlData+ " pgNumber "+ i);
                if (htmlData.htmlType.equals(HtmlType.fiStpPrc))
                    htmlGenerator.fiStpPrc(htmlData.txt);
                else if (htmlData.htmlType.equals(HtmlType.fiStpQst)) {
                    htmlGenerator.fiStpQst(htmlData.txt);
                }
                else if (htmlData.htmlType.equals(HtmlType.fiPosEnd)){
                    view.PG.get(i).doc="fi_endOK.html";
                }
                else if (htmlData.htmlType.equals(HtmlType.fiNegEnd)){
                    view.PG.get(i).doc="fi_notResolved.html";
                }
            }
            if (view.PG.get(i).doc==null || view.PG.get(i).doc.isEmpty() ) {
                view.PG.get(i).doc = view.ID + "-step-" + (i + 1) + ".html";
                view.PG.get(i).htmlObj.htmlObject=htmlGenerator.toHtml();
            }
        }
        return this;
    }

    private FiFactory doc(HtmlObj htmlObj){
        HtmlGenerator htmlGen=new HtmlGenerator();
        htmlGen.fiTitle(view.lbl);
        if (HtmlType.fiStpDsc.name().equals(htmlObj.getHtmlData()[1].htmlType.name())) {
            htmlGen.fiStpDsc(htmlObj.getHtmlData()[1].txt);
        }
        view.htmlObject=htmlGen.toHtml();
        view.doc=view.ID+"step-0.html";
        return this;
    }
    private FiFactory type(String typ){
        view.typ=typ;
        return this;
    }
    private FiFactory newV(String newV){
        view.newV=newV;
        return this;
    }
    private FiFactory v(String v){
        view.v=v;
        return this;
    }
    private FiFactory pd(String num){
        view.pd=num;
        return this;
    }

    private FiFactory kd(String kd){
        view.kd=kd;
        return this;
    }

    private FiFactory ID(String ID) {
        view.ID= ID;
        return this;
    }

    private FiFactory nPg(String nPg) {
        view.nPg= nPg;
        return this;
    }

    private FiFactory findFiLbl(HtmlObj htmlObj) throws DataFactoryNotFoundException {
        if (htmlObj.getHtmlData()[0].htmlType.equals(HtmlType.fiTitle)) {
            view.lbl = htmlObj.getHtmlData()[0].txt;
        }
       else
            throw new DataFactoryNotFoundException("fiTitle",htmlObj.toString());
        return this;
    }


}
