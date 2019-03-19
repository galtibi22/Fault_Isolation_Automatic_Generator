package org.afeka.fi.tests.units;

import org.afeka.fi.backend.common.FiCommonTest;
import org.junit.Test;

public class ViewFactoryTests extends FiCommonTest {

    @Test
    public void generateNdObject() throws Exception {
/*        String sourceDoc="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.docx";
        String resultPath="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.json";
        String resultDic="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\ndFactoryResult\\";
        PythonClient pythonClient=new PythonClient();
        String fiJson=pythonClient.runFiGenerator(sourceDoc,resultPath);
        NdFactory ndFactory=new NdFactory("Nd generator test","alpha-1.0.0");
        ndFactory.add(fiJson);
       // ndFactory.addFi(fiJson);
        ndFactory.generateNdDoc();
        ndFactory.export(resultDic);*/
    }

    @Test
    public void generateTreFactory() throws Exception {
       /* String fiArray= Helpers.readFile(FiProperties.DATA_PATH+"fiArray");
        NdFactory ndFactory=new NdFactory("Nd generator test","alpha-1.0.0");
        TreFactory treFactory=new TreFactory();
        ndFactory.addFis(fiArray);
        treFactory.add(ndFactory.get());
        treFactory.export(FiProperties.DATA_PATH+"results/");*/
        //

    }
}
