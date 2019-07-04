package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.common.*;
import org.afeka.fi.backend.exception.DataFactoryNotFoundException;
import org.afeka.fi.backend.exception.PgLoopException;
import org.afeka.fi.backend.generator.FieldGenerator;
import org.afeka.fi.backend.generator.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.afeka.fi.backend.pojo.internal.PgNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class FiFactory extends ViewFactory<FI> {
    PgFactory pgFactory=new PgFactory();

    @Value("${levelsinfitre}")
    public int levelOfstepInFiTre;
    public FI newFI(FI fiSource,String ndId,Long fiDocId) throws DataFactoryNotFoundException {
        view=new FI();
        logger.called("newFI","ndId "+ndId+" pgs ",fiSource.PG);
        PG pgZero=fiSource.PG.stream().filter(pgi -> pgi._n.equals("0")).findAny().orElseThrow(() -> new DataFactoryNotFoundException("Cannot find PG with n=0"));
        if (pgZero.status.equals(Status.success.name())){
            findFiLbl(pgZero.htmlObj).ID(Helpers.removeSpecialChars(view.lbl)+"_"+ FieldGenerator.id()).
                    doc(pgZero.htmlObj);
        }
        else {
            ID(FieldGenerator.id());
            view.lbl="Cannot generate lbl for fi_"+ FieldGenerator.id();
            pgZero.Y=new YN();
            pgZero.N=new YN();
        }
        return
                type("10").kd("0").
                pgs(/*fiSource.PG.subList(1,fiSource.PG.size())*/fiSource.PG).
                newV("0").
                v("-").
                pd("60").
                nPg(fiSource.PG.size()-1+"").
                ndId(ndId).
                fiDocId(fiDocId).
                status(fiSource.PG).
                fiJson().
                get();
    }

    private FiFactory ndId(String ndId) {
        view.ndId=ndId;
        return this;
    }

    private FiFactory status(List<PG> pgs) {
        if(pgs.stream().allMatch(p->p.status.equals(Status.success.name())))
            view.status=Status.success;
        else
            view.status=Status.failed;
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
         htmlGenerator = new HtmlGenerator();
        htmlGenerator.fiTitle(fi.lbl);
        logger.info("export fi html with name=" + fi.doc);
        save(htmlGenerator.toHtml(), Paths.get(path + "/" + fi.doc));
        for (PG pg : fi.PG) {
            pgFactory.export(path,pg);
        }
    }

    private FiFactory pgs(List<PG> pgs) {
    //view.PG= pgs;
        pgs.forEach(pg->
        view.PG.add(pgFactory.newPg(pg,view.ID))
    );
        validatePgsTree();
    return this;
}

    private void validatePgsTree() {
            List<PgNode> pgNodes = new ArrayList<>();
            List<PG> pgs = view.PG.subList(1, view.PG.size());
            pgs.forEach(pg -> pgNodes.add(new PgNode(Integer.parseInt(pg._n) - 1)));
            pgs.forEach(pg ->
                    pgFactory.validatePgToSyntax(pg,pgNodes,view.ID));
        try {
            pgFactory.validatePgPath(pgNodes.get(0),-1);
        }catch (PgLoopException e){
            logger.error("Found fiPathLoopError in fi "+view.ID+" in pg n "+e.getNumber());
            pgs.get(e.getNumber()).status=Status.FiPathLoopError.name();

        }
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
            view.lbl = htmlObj.getHtmlData()[0].txt.replaceAll("  "," ");
        }
       else
            throw new DataFactoryNotFoundException("fiTitle",htmlObj.toString());
        return this;
    }


}
