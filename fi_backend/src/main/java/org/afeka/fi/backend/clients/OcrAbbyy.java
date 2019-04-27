package org.afeka.fi.backend.clients;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class OcrAbbyy implements OcrClient {
    Logger logger=Logger.getLogger(this.getClass().getName());
    @Override
    public MultipartFile run(MultipartFile file) throws Exception {
        logger.info("Start AbbyClient image to doc process");
        loadEngine();
        setupFREngine();
        Path source=Files.createTempFile("abbyy",".jpg");
        file.transferTo(source);
        Path result=Files.createTempFile("resu;t",".doc");
        processImage(source.toFile().getAbsolutePath(),result.toFile().getAbsolutePath());
        unloadEngine();
        MultipartFile fiDoc = new MockMultipartFile("fiDoc.doc",new FileInputStream(result.toFile()));
        return fiDoc;
    }

    private void loadEngine() throws Exception {
        logger.info( "Initializing Engine..." );
        //engine = Engine.InitializeEngine( FiProperties.ABBY_DLL_PATH, FiProperties.ABBY_PROJECTID,
    }

    private void setupFREngine() {
        String predefined = "Version9Compatibility";
        logger.info("Loading predefined profile " + predefined);
        // engine.LoadPredefinedProfile( predefined );
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
            //*  IFRDocument document = engine.CreateFRDocument();

            try {
                // Add image file to document
                logger.info( "Loading image from path="+sourcePath );
                //*    document.AddImageFile( sourcePath, null, null );
                // Process document
                logger.info( "Start Process the image" );
                //*  document.Process( null );
                // Save results
                logger.info( "Save results to rtf with default parameters. resultPath="+resultPath );
                // String rtfExportPath =;
                //*  document.Export( resultPath, FileExportFormatEnum.FEF_RTF, null );
                // Save results to pdf using 'balanced' scenario
                // IPDFExportParams pdfParams = engine.CreatePDFExportParams();
                // pdfParams.setScenario( PDFExportScenarioEnum.PES_Balanced );

                //String pdfExportPath = AbbyConfig.GetSamplesFolder() + "\\SampleImages\\Demo.pdf";
                //document.Export( pdfExportPath, FileExportFormatEnum.FEF_PDF, pdfParams );
            } finally {
                // Close document
                //*   document.Close();
            }
        } catch( Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void unloadEngine() throws Exception {
        logger.info( "Deinitializing Abby Engine..." );
        //*   engine = null;
        //*   Engine.DeinitializeEngine();
    }
}
