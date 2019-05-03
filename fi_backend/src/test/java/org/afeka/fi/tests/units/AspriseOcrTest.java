package org.afeka.fi.tests.units;

import com.asprise.ocr.Ocr;
import org.afeka.fi.tests.common.FiCommonTest;
import org.junit.Test;

import java.io.File;

public class AspriseOcrTest extends FiCommonTest {

    @Test
    public void basicOcr(){
        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_SLOW); // English
        String s = ocr.recognize(new File[] {new File("/Users/gal.tibi/afekaProjects/Fault_Isolation_Automatic_Generator/data/flowchart_excelent_size_12.png")},
                Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT,Ocr.PROP_PDF_OUTPUT_FILE,"/Users/gal.tibi/afekaProjects/Fault_Isolation_Automatic_Generator/data/gal.xml"); // PLAINTEXT | XML | PDF | RTF
        System.out.println("Result: " + s);
        ocr.stopEngine();
    }
}
