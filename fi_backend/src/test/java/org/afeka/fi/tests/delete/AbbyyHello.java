package org.afeka.fi.tests.delete;

import com.abbyy.FREngine.*;

import java.nio.file.*;

public class AbbyyHello {

    public static void main( String[] args ) {
        try {
            AbbyyHello application = new AbbyyHello();
            application.Run();
        } catch( Exception ex ) {
            displayMessage( ex.getMessage() );
        }
    }

    public void Run() throws Exception {
        // Load ABBYY FineReader Engine
        loadEngine();
        try{
            // Process with ABBYY FineReader Engine
            processWithEngine();
        } finally {
            // Unload ABBYY FineReader Engine
            unloadEngine();
        }
    }

    private void loadEngine() throws Exception {
        displayMessage( "Initializing Engine..." );
        engine = Engine.InitializeEngine( AbbyConfig.GetDllFolder(), AbbyConfig.GetCustomerProjectId(),
                AbbyConfig.GetLicensePath(), AbbyConfig.GetLicensePassword(), "", "", false );
    }

    private void processWithEngine() {
        try {
            // Setup FREngine
            setupFREngine();

            // Process sample image
            processImage();
        } catch( Exception ex ) {
            displayMessage( ex.getMessage() );
        }
    }

    private void setupFREngine() {
        displayMessage( "Loading predefined profile..." );
        engine.LoadPredefinedProfile( "DocumentConversion_Accuracy" );
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

    private void processImage() {
        String imagePath ="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.JPG";
                //AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Capture.JPG";

        try {
            // Don't recognize PDF file with a textual content, just copy it

     if( engine.IsPdfWithTextualContent( imagePath, null ) ) {
                displayMessage( "Copy results..." );
                String resultPath = AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Demo_copy.pdf";
                Files.copy( Paths.get( imagePath ), Paths.get( resultPath ), StandardCopyOption.REPLACE_EXISTING );
                return;
            }


            // Create document
            IFRDocument document = engine.CreateFRDocument();

            try {
                // Add image file to document
                displayMessage( "Loading image..." );
                document.AddImageFile( imagePath, null, null );

                // Process document
                displayMessage( "Process..." );
                document.Process( null );

                // Save results
                displayMessage( "Saving results..." );

                // Save results to rtf with default parameters
                String rtfExportPath = AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Demo.docx";
                document.Export( rtfExportPath, FileExportFormatEnum.FEF_RTF, null );

                // Save results to pdf using 'balanced' scenario
                IPDFExportParams pdfParams = engine.CreatePDFExportParams();
                pdfParams.setScenario( PDFExportScenarioEnum.PES_Balanced );

                //String pdfExportPath = AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Demo.pdf";
                //document.Export( pdfExportPath, FileExportFormatEnum.FEF_PDF, pdfParams );
            } finally {
                // Close document
                document.Close();
            }
        } catch( Exception ex ) {
            displayMessage( ex.getMessage() );
        }
    }

    private void unloadEngine() throws Exception {
        displayMessage( "Deinitializing Engine..." );
        engine = null;
        Engine.DeinitializeEngine();
    }

    private static void displayMessage( String message ) {
        System.out.println( message );
    }

    private IEngine engine = null;
}

