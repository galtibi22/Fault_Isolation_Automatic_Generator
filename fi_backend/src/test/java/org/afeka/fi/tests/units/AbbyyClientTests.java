package org.afeka.fi.tests.units;

import org.afeka.fi.backend.clients.AbbyClient;
import org.afeka.fi.backend.common.FiCommonTest;
import org.junit.Test;

public class AbbyyClientTests extends FiCommonTest {

    @Test
    public void runAbby() throws Exception {
        AbbyClient.runAbby("C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\fi_table_image_excelent.jpg",
                "C:\\Users\\galti\\IdeaProjects\\Fault_Isolation_Automatic_Generator\\data\\fi_table_image_excelent.doc");
    }
}