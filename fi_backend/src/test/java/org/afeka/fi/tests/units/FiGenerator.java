package org.afeka.fi.tests.units;

import org.afeka.fi.backend.common.FiCommonTest;
import org.junit.Test;

public class FiGenerator extends FiCommonTest {
//C:\Users\galti\IdeaProjects\Fault_Isolation_Automatic_Generator\data\
    @Test
    public void runFiGeneratorImageToArrayJson() throws Exception {
        String source="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.JPG";
        String sourceDoc=String.format("C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.docx");

        String result="C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\NoCommunicationwithLeftWagon.json";
        //FiGeneratorClient client=new FiGeneratorClient();
        //String fiJson=client.runGenerator(source,sourceDoc,result);
        //client.jsonToFiObject(fiJson);
        System.out.println("please dont wait");

    }
}
