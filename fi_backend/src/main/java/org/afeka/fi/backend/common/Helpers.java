package org.afeka.fi.backend.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.afeka.fi.backend.pojo.auth.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public static String getFileExtension(MultipartFile file) {
        int point=file.getOriginalFilename().lastIndexOf(".");
        return file.getOriginalFilename().substring(point+1);
    }

    public static void saveFile(String path,MultipartFile file) throws IOException {
        Files.write(Paths.get(path),file.getBytes());
    }


    public static User decodeBasicAuth(String auth){
        String base64Credentials = auth.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);
        String email=values[0];String password=values[1];
        return new User(values[0],values[1]);
    }

    public static void zip(Path sourceDirPath, Path zipFilePath) throws IOException {
//        Path p = Files.createFile(zipFilePath);
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(zipFilePath))) {
            Files.walk(sourceDirPath)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
    }

    public static String removeSpecialChars(String lbl) {
        return lbl.replaceAll("\"[\\\\-\\\\+\\\\.\\\\^:,]\",\"\"","").replaceAll(" ","");
    }

/*    public static void zip(Path source,Path result) throws IOException {
        FileOutputStream fos = new FileOutputStream(result.toFile());
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = source.toFile();
        for (File file : fileToZip.listFiles()) {
            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();

    }*/
       // fis.close();
       // fos.close();
    //}
}
