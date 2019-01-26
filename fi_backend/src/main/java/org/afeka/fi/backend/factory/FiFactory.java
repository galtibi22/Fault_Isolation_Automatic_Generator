package org.afeka.fi.backend.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.afeka.fi.backend.common.*;
import org.afeka.fi.backend.exception.DataNotFoundException;
import org.afeka.fi.backend.html.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.*;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class FiFactory extends FiCommon {
    public FI fi=new FI();


    public FiFactory(String fiJson,String id) throws DataNotFoundException {
        logger.info("FiFactory called");
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        List<PG> pgs=new ArrayList<PG>();
        pgs=gson.fromJson(fiJson,FI.class).PG;

        findFiLbl(pgs.get(0).htmlObj).ID(id).type("10").kd("0").doc(pgs.get(0).htmlObj).
                pgs(pgs.subList(1,pgs.size())).newV("0").v("-").pd("60").
                nPg(pgs.size()+"");
    }

    public static void exportFi(FI fi,String resultPath) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(resultPath+fi.doc);
        out.println(fi.htmlObject.renderFormatted());
        out.close();
        for (PG pg:fi.PG){
            if (pg.htmlObj.htmlObject!=null) {
                FiLogger.getLogger("FiFactory").info("Generate pg html with name="+pg.doc);
                out = new PrintWriter(resultPath + pg.doc);
                out.println(pg.htmlObj.htmlObject.renderFormatted());
                out.close();
            }
        }

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
        if (htmlObj.getHtmlData()[1].htmlType.name().equals(HtmlType.fiStpDsc)) {
            htmlGen.fiStpDsc(htmlObj.getHtmlData()[1].txt);
        }
        fi.htmlObject=htmlGen.toHtml();
        fi.doc=fi.ID+"step-0.html";
        return this;
    }
    private FiFactory type(String type){
        fi.typ=type;
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

    public FiFactory nPg(String nPg) {
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
    /**
     * <TRE v="0806" mxPgs="12" srch="111101" nLnkCols="3" lnkCol0="manNm" lnkCol0tl="Manual Name" lnkCol0w="30" lnkCol1="chpNm" lnkCol1tl="Chapter Name" lnkCol1w="30" lnkCol2="nm" lnkCol2tl="Target Name" lnkCol2w="40" fiRigid="0" prnt="000000" ful="1">
     * <ND lbl="UAV SYSTEM" ID="0" typ="0" kIdDsp="" kd="1" nPg="1" doc="SEARCHER_MKII.html" pic="SEARCHER_MKII.png" newV="0" v="-" pdf="" pd="0">
     *   <FI lbl="CBX fail" ID="(IN)SH-06-7IAF_3.14_1" typ="10" kIdDsp="" kd="0" nPg="16" doc="(IN)SH-06-7IAF_3.14_1-step-0.html" newV="0" v="-" pdf="" pd="60">
     */


}
