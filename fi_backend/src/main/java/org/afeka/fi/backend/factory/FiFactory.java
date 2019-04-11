package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.*;
import org.afeka.fi.backend.exception.DataFactoryNotFoundException;
import org.afeka.fi.backend.html.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FiFactory extends ViewFactory<FI> {
    public FI newFI(List<PG> pgs,String ndId,Long fiDocId) throws DataFactoryNotFoundException {
        view=new FI();
        logger.called("newFI","ndId "+ndId+" pgs ",pgs);
        PG pgZero=pgs.stream().filter(pgi -> pgi._n.equals("0")).findAny().orElseThrow(() -> new DataFactoryNotFoundException("Cannot find PG with n=0"));
        return findFiLbl(pgZero.htmlObj).
                ID(Helpers.removeSpecialChars(view.lbl)+"_"+ Generator.id()).
                type("10").kd("0").
                doc(pgZero.htmlObj).
                pgs(pgs.subList(1,pgs.size())).
                newV("0").
                v("-").
                pd("60").
                nPg(pgs.size()-1+"").
                ndId(ndId).
                fiDocId(fiDocId).
                fiJson().
                get();
    }

    private FiFactory ndId(String ndId) {
        view.ndId=ndId;
        return this;
    }

    private FiFactory fiDocId(Long fiDocId) {
        view.fiDocId=fiDocId;
        return this;
    }

    private FiFactory fiJson() {
        view.fiJson=Helpers.initGson().toJson(view);
        return this;
    }


    @Override
    public void export(Path path, FI fi) throws IOException {
        HtmlGenerator htmlGen = new HtmlGenerator();
        htmlGen.fiTitle(fi.lbl);
        if (HtmlType.fiStpDsc.name().equals(fi.htmlObject.getHtmlData()[1].htmlType.name())) {
            htmlGen.fiStpDsc(fi.htmlObject.getHtmlData()[1].txt);
        }
        logger.info("export fi html with name=" + fi.doc);
        save(htmlGen.toHtml().renderFormatted(), Paths.get(path + "/" + fi.doc));
        for (PG pg : fi.PG) {
            htmlGen = new HtmlGenerator();
            for (HtmlData htmlData : pg.htmlObj.getHtmlData()) {
                if (htmlData.htmlType.equals(HtmlType.fiStpPrc)) htmlGen.fiStpPrc(htmlData.txt);
                else if (htmlData.htmlType.equals(HtmlType.fiStpQst)) htmlGen.fiStpQst(htmlData.txt);
            }
            logger.info("export pg html with name=" + pg.doc);
            save(htmlGen.toHtml().renderFormatted(), Paths.get(path + "/" + pg.doc));
        }
    }

private FiFactory pgs(List<PG> pgs) {
    view.PG= pgs;

    for (int i=0;i<view.PG.size();i++) {
        for (HtmlData htmlData : view.PG.get(i).htmlObj.getHtmlData())
            if (htmlData.htmlType.equals(HtmlType.fiPosEnd)) {
                view.PG.get(i).doc = "fi_endOK.html";
                break;
            } else if (htmlData.htmlType.equals(HtmlType.fiNegEnd)) {
                view.PG.get(i).doc = "fi_notResolved.html";
                break;
            } else {
                view.PG.get(i).doc = view.ID + "-step-" + (i + 1) + ".html";
                break;
            }
    }
    return this;
}

    private FiFactory doc(HtmlObj htmlObj){
        view.htmlObject=htmlObj;
        view.doc=view.ID+"-step-0.html";
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
            view.lbl = Helpers.removeSpecialChars(htmlObj.getHtmlData()[0].txt);
        }
       else
            throw new DataFactoryNotFoundException("fiTitle",htmlObj.toString());
        return this;
    }


}
