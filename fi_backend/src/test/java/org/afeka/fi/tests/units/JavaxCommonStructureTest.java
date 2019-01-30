package org.afeka.fi.tests.units;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.afeka.fi.backend.common.FiProperties;
import org.afeka.fi.backend.pojo.commonstructure.FI;
import org.afeka.fi.backend.pojo.commonstructure.TRE;
import org.afeka.fi.backend.common.FiCommonTest;
import org.junit.Before;
import org.junit.Test;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;


public class JavaxCommonStructureTest extends FiCommonTest {

    JAXBContext jaxbContext;

    @Before
    public void before() throws JAXBException {
         jaxbContext = JAXBContext.newInstance(TRE.class);
    }

    @Test
        public void testTreXmlToObject() throws Exception {
           TRE tre=treXmlToObject(FiProperties.DATA_PATH+FiProperties.VIEW_DEMO_FITRE_NAME);
            logger.info(tre);
        }

        @Test
        public void objectToTreXml() throws Exception {
            StringWriter sw = new StringWriter();
            TRE tre=treXmlToObject(FiProperties.DATA_PATH+FiProperties.VIEW_DEMO_FITRE_NAME);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(tre,  new FileOutputStream(FiProperties.VIEW_DEMO_FITRE_PATH+FiProperties.VIEW_DEMO_FITRE_NAME));
        }
    @Test
    public void testTreXmlToJson() throws Exception {
        TRE tre=treXmlToObject(FiProperties.DATA_PATH+FiProperties.VIEW_DEMO_FITRE_NAME);
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String json=gson.toJson(tre.ND.get(0).ND.get(0).ND.get(0).FI.get(0), FI.class);
        logger.info(json);
    }
        private TRE treXmlToObject(String path) throws JAXBException {
            File file = new File(path);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            TRE tre= (TRE) unmarshaller.unmarshal(file);
            return tre;
        }

}
