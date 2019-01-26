package org.afeka.fi.backend.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Helpers {
    public static String inputStreamToString(InputStream inputStream) throws IOException {
        String line;
        StringBuilder builder=new StringBuilder();
        BufferedReader brCleanUp =
                new BufferedReader (new InputStreamReader(inputStream));
        while ((line = brCleanUp.readLine ()) != null) {
            builder.append (line+"\n");
        }
        brCleanUp.close();
        return builder.toString();
    }
}
