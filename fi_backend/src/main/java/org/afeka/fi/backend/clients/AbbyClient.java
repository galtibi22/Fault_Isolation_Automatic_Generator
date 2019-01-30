package org.afeka.fi.backend.clients;

import com.abbyy.FREngine.*;
import org.afeka.fi.backend.common.FiCommon;
import org.afeka.fi.backend.common.FiCommonTest;
import org.afeka.fi.backend.common.FiProperties;
import org.junit.Test;

public class AbbyClient extends FiCommon {

    public static void runAbby(String sourcePath,String resultPath) throws Exception {
        AbbyClient abbyClient=new AbbyClient();
        abbyClient.run(sourcePath,resultPath);
    }
    private void run(String sourcePath,String resultPath) throws Exception {
        logger.info("Start AbbyClient image to doc process");
        loadEngine();
        setupFREngine();
        processImage(sourcePath,resultPath);
        unloadEngine();

    }


    private IEngine engine = null;

    private void unloadEngine() throws Exception {
        logger.info( "Deinitializing Abby Engine..." );
        engine = null;
        Engine.DeinitializeEngine();
    }

    private void loadEngine() throws Exception {
        logger.info( "Initializing Engine..." );
        engine = Engine.InitializeEngine( FiProperties.ABBY_DLL_PATH, FiProperties.ABBY_PROJECTID,
                ""/*SamplesConfig.GetLicensePath()*/,"" /* SamplesConfig.GetLicensePassword()*/, "", "", false );
    }

    private void setupFREngine() {
        String predefined="TextExtraction_Accuracy";
        logger.info( "Loading predefined profile "+predefined );
        engine.LoadPredefinedProfile( predefined );
        // Possible profile names are:
        //   "DocumentConversion_Accuracy", "DocumentConversion_Speed",
        //   "DocumentArchiving_Accuracy", "DocumentArchiving_Speed",
        //   "BookArchiving_Accuracy", "BookArchiving_Speed",
        //   "TextExtraction_Accuracy", "TextExtraction_Speed",
        //   "FieldLevelRecognition",
        //   "BarcodeRecognition_Accuracy", "BarcodeRecognition_Speed",
        //   "HighCompressedImageOnlyPdf",
        //   "BusinessCardsProcessing",
        //   "EngineeringDrawingsProcessing",
        //   "Version9Compatibility",
        //   "Default"
    }
    private void processImage(String sourcePath,String resultPath) {
      //  String imagePath = sourcePath;
        try {
            // Don't recognize PDF file with a textual content, just copy it
       /*     if( engine.IsPdfWithTextualContent( imagePath, null ) ) {
                displayMessage( "Copy results..." );
                String resultPath = AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Demo_copy.pdf";
                Files.copy( Paths.get( imagePath ), Paths.get( resultPath ), StandardCopyOption.REPLACE_EXISTING );
                return;
            }
*/
            // Create document
            IFRDocument document = engine.CreateFRDocument();

            try {
                // Add image file to document
                logger.info( "Loading image from path="+sourcePath );
                document.AddImageFile( sourcePath, null, null );
                // Process document
                logger.info( "Start Process the image" );
                document.Process( null );
                // Save results
                logger.info( "Save results to rtf with default parameters. resultPath="+resultPath );
               // String rtfExportPath =;
                document.Export( resultPath, FileExportFormatEnum.FEF_RTF, null );
                // Save results to pdf using 'balanced' scenario
               // IPDFExportParams pdfParams = engine.CreatePDFExportParams();
               // pdfParams.setScenario( PDFExportScenarioEnum.PES_Balanced );

                //String pdfExportPath = AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Demo.pdf";
                //document.Export( pdfExportPath, FileExportFormatEnum.FEF_PDF, pdfParams );
            } finally {
                // Close document
                document.Close();
            }
        } catch( Exception ex ) {
            logger.error(ex);
        }
    }


}

