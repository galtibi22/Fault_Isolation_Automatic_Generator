package org.afeka.fi.tests.units;
import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.*;

public class TestArct {

    public static String getImgText(File imageLocation) {
        ITesseract instance = new Tesseract();
        try
        {
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
            instance.setDatapath(tessDataFolder.getAbsolutePath());
            instance.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_CUBE_ONLY);
            //File f=new File("C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\fi_backend");
            //System.out.print(f.isDirectory());
           // instance.setDatapath(f.getAbsolutePath());
            String imgText = instance.doOCR(imageLocation);
            return imgText;
        }
        catch (TesseractException e)
        {
            e.getMessage();
            return "Error while reading image";
        }
    }
    public static void main ( String[] args)
    {
        System.out.println(System.getProperty("user.dir"));
        File file=new File("table1.jpg");
        //System.out.println(file.isFile());
        System.out.println(getImgText(file));
    }
}