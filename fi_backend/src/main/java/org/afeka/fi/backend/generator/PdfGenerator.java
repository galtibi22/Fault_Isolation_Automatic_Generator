package org.afeka.fi.backend.generator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.http.PgBoundery;
import org.afeka.fi.backend.pojo.commonstructure.Status;
import org.afeka.fi.backend.pojo.commonstructure.TRE;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static com.itextpdf.text.FontFactory.COURIER;

public class PdfGenerator extends FiCommon {
    Document document;
    PdfPTable table;

    public PdfGenerator(Path path) throws FileNotFoundException, DocumentException {
        document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path.toFile()));

    }

    public void addTreToReport(TRE tre) throws DocumentException {
        logger.called("addTreToReport","tre",tre.ID);
        document.open();
        AtomicInteger counter= new AtomicInteger(1);
        addTitle("Project: "+tre.lbl,20,Font.BOLD);
        tre.ndParents.forEach(ndParent -> {
                    try {
                        addTitle("Module: "+ndParent.lbl,18,Font.BOLD);
                    } catch (DocumentException e) {
                        logger.error(e);
                    }
                    ndParent.ND.forEach(nd -> {
                        try {
                            addTitle("Part:"+nd.lbl,16,Font.BOLDITALIC);
                        } catch (DocumentException e) {
                            logger.error(e);
                        }
                        nd.FI.forEach(fi -> {
                            try {
                                addFiToReport(fi, counter);
                            } catch (DocumentException e) {
                                logger.error(e);
                            }
                        });
                    });
                }
        );
        document.close();
    }

    private void addTitle(String text, int fontSize,int style) throws DocumentException {
        Font font = FontFactory.getFont(COURIER, fontSize,style,BaseColor.BLACK);
        document.add( new Chunk(text, font));
        document.add(new Paragraph("\n"));
    }
    private void newCell(String text, int fontType,BaseColor baseColor) {
        Font font = FontFactory.getFont(COURIER, 6,fontType,baseColor);
                //new Font(Font.FontFamily.SYMBOL, 6, fontType);
        Paragraph paragraph=new Paragraph(text);
        paragraph.setFont(font);
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(paragraph);
        table.addCell(cell);
    }

    private void addRowToTable(PgBoundery pgBoundery) {
        newCell(pgBoundery.Number,Font.BOLD,BaseColor.BLACK);
        newCell(pgBoundery.Type,Font.NORMAL,BaseColor.BLACK);
        if (pgBoundery.status.equals(Status.failed))
            newCell(pgBoundery.status,Font.NORMAL,BaseColor.RED);
        else
            newCell(pgBoundery.status,Font.NORMAL,BaseColor.BLACK);
        newCell(pgBoundery.Description,Font.NORMAL,BaseColor.BLACK);
        newCell(pgBoundery.toYes,Font.NORMAL,BaseColor.BLACK);
        newCell(pgBoundery.toNo,Font.NORMAL,BaseColor.BLACK);
        newCell(pgBoundery.taskLink,Font.NORMAL,BaseColor.BLACK);

    }

    private void addFiToReport(FI fi,AtomicInteger counter) throws DocumentException {
        logger.info("Start build new table for fi "+fi.ID);
        addTitle(counter.get()+". FI: "+fi.lbl,14,Font.NORMAL);
        counter.getAndIncrement();
        newTable();
        fi.pgBounderies.forEach(pgBoundery -> addRowToTable(pgBoundery));
        document.add(table);
    }
    private void newTable() throws DocumentException {
        table = new PdfPTable(7);
        table.setWidths(new int[]{1, 1, 1,2,1,1,1});
        Stream.of("Number","Type","Status","Description","To Yes","To No","Task link")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }



}
