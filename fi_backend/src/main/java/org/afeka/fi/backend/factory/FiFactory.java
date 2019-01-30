package org.afeka.fi.backend.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.afeka.fi.backend.common.*;
import org.afeka.fi.backend.exception.DataNotFoundException;
import org.afeka.fi.backend.html.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class FiFactory extends ViewFactory {
    public FI fi;

    public FiFactory(String fiJson,String id) throws DataNotFoundException {
        logger.info("FiFactory called");
        fi=new FI();
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        List<PG> pgs;
        pgs=gson.fromJson(fiJson,FI.class).PG;

        findFiLbl(pgs.get(0).htmlObj).ID(id).type("10").kd("0").doc(pgs.get(0).htmlObj).
                pgs(pgs.subList(1,pgs.size())).newV("0").v("-").pd("60").
                nPg(pgs.size()+"");
    }

    public FiFactory(FI fi){
        this.fi=fi;
    }

    @Override
    public void export(String path) throws IOException {
        save(fi.htmlObject.renderFormatted(),path+fi.doc);
        for (PG pg:fi.PG){
            if (pg.htmlObj.htmlObject!=null) {
                logger.info("export pg html with name="+pg.doc);
                save(pg.htmlObj.htmlObject.renderFormatted(),path+pg.doc);
            }
        }

    }

    @Override
    public void add(Object o) throws Exception {
        PG pg=(PG)o;
        throw new Exception("Not implement yet");
    }

    private FiFactory pgs(List<PG> pgs) {
        fi.PG= pgs;
        for (int i=0;i<fi.PG.size();i++){
            HtmlGenerator htmlGenerator=new HtmlGenerator();
            for(HtmlData htmlData:fi.PG.get(i).htmlObj.getHtmlData()){
                if (htmlData.htmlType.equals(HtmlType.fiStpPrc))
                    htmlGenerator.fiStpPrc(htmlData.txt);
                else if (htmlData.htmlType.equals(HtmlType.fiStpQst)) {
                    htmlGenerator.fiStpQst(htmlData.txt);
                }
                else if (htmlData.htmlType.equals(HtmlType.fiPosEnd)){
                    fi.PG.get(i).doc="fi_endOK.html";
                }
                else if (htmlData.htmlType.equals(HtmlType.fiNegEnd)){
                    fi.PG.get(i).doc="fi_notResolved.html";
                }
            }
            if (fi.PG.get(i).doc==null || fi.PG.get(i).doc.isEmpty() ) {
                fi.PG.get(i).doc = fi.ID + "-step-" + (i + 1) + ".html";
                fi.PG.get(i).htmlObj.htmlObject=htmlGenerator.toHtml();
            }
        }
        return this;
    }

    private FiFactory doc(HtmlObj htmlObj){
        HtmlGenerator htmlGen=new HtmlGenerator();
        htmlGen.fiTitle(fi.lbl);
        if (HtmlType.fiStpDsc.name().equals(htmlObj.getHtmlData()[1].htmlType.name())) {
            htmlGen.fiStpDsc(htmlObj.getHtmlData()[1].txt);
        }
        fi.htmlObject=htmlGen.toHtml();
        fi.doc=fi.ID+"step-0.html";
        return this;
    }
    private FiFactory type(String typ){
        fi.typ=typ;
        return this;
    }
    private FiFactory newV(String newV){
        fi.newV=newV;
        return this;
    }
    private FiFactory v(String v){
       fi.v=v;
        return this;
    }
    private FiFactory pd(String num){
        fi.pd=num;
        return this;
    }

    private FiFactory kd(String kd){
        fi.kd=kd;
        return this;
    }

    private FiFactory ID(String ID) {
        fi.ID= ID;
        return this;
    }

    private FiFactory nPg(String nPg) {
        fi.nPg= nPg;
        return this;
    }

    private FiFactory findFiLbl(HtmlObj htmlObj) throws DataNotFoundException {
        if (htmlObj.getHtmlData()[0].htmlType.equals(HtmlType.fiTitle)) {
            fi.lbl = htmlObj.getHtmlData()[0].txt;
        }
       else
            throw new DataNotFoundException("fiTitle",htmlObj.toString());
        return this;
    }


}
