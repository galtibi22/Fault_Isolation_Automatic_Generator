package org.afeka.fi.backend.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }

    public static Gson initGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        builder.setPrettyPrinting();
        return builder.create();
    }
}
