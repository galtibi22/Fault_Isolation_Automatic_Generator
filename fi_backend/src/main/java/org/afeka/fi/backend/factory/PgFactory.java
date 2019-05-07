package org.afeka.fi.backend.factory;

import org.afeka.fi.backend.exception.PgLoopException;
import org.afeka.fi.backend.generator.HtmlGenerator;
import org.afeka.fi.backend.pojo.commonstructure.*;
import org.afeka.fi.backend.pojo.internal.PgNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class PgFactory extends ViewFactory<PG> {
    @Override
    public void export(Path path, PG pg) throws IOException {
        logger.info("export pg html with name=" + pg.doc);
        htmlGenerator = new HtmlGenerator();
        for (HtmlData htmlData : pg.htmlObj.getHtmlData()) {
            if (htmlData.htmlType.equals(HtmlType.fiStpPrc)) htmlGenerator.fiStpPrc(htmlData.txt);
            else if (htmlData.htmlType.equals(HtmlType.fiStpQst)) htmlGenerator.fiStpQst(htmlData.txt);
        }
        save(htmlGenerator.toHtml().renderFormatted(), Paths.get(path + "/" + pg.doc));
    }

    public PG newPg(PG pg,String fiId) {
        if ( pg.status.equals(Status.success.name()))
            for (HtmlData htmlData : pg.htmlObj.getHtmlData()) {
                if (htmlData.htmlType.equals(HtmlType.fiPosEnd)) {
                    pg.doc = "fi_endOK.html";
                    break;
                } else if (htmlData.htmlType.equals(HtmlType.fiNegEnd)) {
                    pg.doc = "fi_notResolved.html";
                    break;
                } else {
                    pg.doc = fiId + "-step-" +pg._n + ".html";
                    break;
                }
            }else{
          //  pg.N=new YN();
          //  pg.Y=new YN();
            pg.htmlObj=new HtmlObj();

        }
        return pg;
    }

    public PG validatePgToSyntax(PG pg,List<PgNode> pgNodes,String fiId) {
        logger.called("validatePgToSyntax","pg",pg.toString());
        if (pg.status.equals(Status.success.name())){
            PgNode current = pgNodes.get(Integer.parseInt(pg._n) - 1);
            if (pg.type != null && pg.type.equals("task")) {
                try { current.setYes(pgNodes.get(Integer.parseInt(pg.Y.getRtY()) - 1)); }
                catch (Exception e) {
                    setStatusError(pg,Status.taskReturnNextYesIncorrectFormat,fiId);
                }
                try {current.setNo(pgNodes.get(Integer.parseInt(pg.Y.getRtN()) - 1)); }
                catch (Exception e) {
                    setStatusError(pg,Status.taskReturnNextNoIncorrectFormat,fiId);

                }
            } else if (pg.type != null && pg.type.equals("test")) {
                try {
                    current.setYes(pgNodes.get(Integer.parseInt(pg.Y.getTo())-1));
                } catch (Exception e) {
                    setStatusError(pg,Status.nextYesIncorrectFormat,fiId);
                }
                try {
                    current.setNo(pgNodes.get(Integer.parseInt(pg.N.getTo())-1));
                } catch (Exception e) {
                    setStatusError(pg,Status.nextNoIncorrectFormat,fiId);

                }
            }
        }
        return pg;
    }

    private PG setStatusError(PG pg, Status status, String fiId) {
        logger.error("Found "+status.name()+" in fi "+fiId+" in pg n "+pg._n);
        pg.status = status.name();
        return pg;
    }

    public void validatePgPath(PgNode pgNode, int parent) throws PgLoopException {
        if (pgNode==null)
            return;
        else if (pgNode.isMarked())
            throw new PgLoopException(parent);
        pgNode.setMarkedarked(true);
        validatePgPath(pgNode.getYes(),pgNode.getN());
        validatePgPath(pgNode.getNo(),pgNode.getN());
        pgNode.setMarkedarked(false);

    }

}
