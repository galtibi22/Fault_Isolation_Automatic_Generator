package org.afeka.fi.tests.units;

import org.afeka.fi.backend.clients.PythonClient;
import org.afeka.fi.backend.factory.NdFactory;
import org.afeka.fi.tests.FiCommonTest;
import org.junit.Test;

public class NdFactoryTest extends FiCommonTest {

    @Test
    public void generateNdObject() throws Exception {
       //TODO: Check why before method not start in the test
       // FiProperties.init();
        String sourceDoc=String.format("C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.docx");
        String resultPath="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.json";
        String resultDic="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\ndFactoryResult\\";
        PythonClient pythonClient=new PythonClient();
        String fiJson=pythonClient.runFiGenerator(sourceDoc,resultPath);
        NdFactory ndFactory=new NdFactory("Nd generator test","alpha-1.0.0");
        ndFactory.addFi(fiJson);
        ndFactory.addFi(fiJson);
        ndFactory.generateNdDoc();
        ndFactory.exportND(resultDic);
    }
}
