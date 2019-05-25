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
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Helpers {
    private static FiLogger logger=new FiLogger();
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

    public static String getFileExtension(String file){
        int point=file.lastIndexOf(".");
        if (point>0)
            return file.substring(point+1);
        else
            return "";


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

    public static Path unzip(Path zipFilePath) throws IOException {
        File dir = Files.createTempDirectory("destDir").toFile();
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath.toFile());
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                if (!getFileExtension(fileName).isEmpty() && !fileName.contains("/")) {
                    File newFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                    System.out.println("Unzipping to " + newFile.getAbsolutePath());
                    //create directories for sub directories in zip
                    new File(newFile.getParent()).mkdirs();
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                    //close this ZipEntry
                    zis.closeEntry();
                }else{

                    logger.error("Cannot unzip "+fileName+" from "+zipFilePath.toString()+" because the file without Extension or a directory");
                }
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Paths.get(dir.toURI());
    }

    public static String removeSpecialChars(String lbl) {
        return lbl.replaceAll("[-+.^:,]","").replaceAll(" ","");
    }

    public static String getFileSimpleName(MultipartFile fiDoc) {
        return fiDoc.getOriginalFilename().substring(0,fiDoc.getOriginalFilename().lastIndexOf("."));
    }
    public static String getFileSimpleName(String file) {
        return file.substring(0,file.lastIndexOf("."));
    }
    public static String encodeBasicAuth(String userName, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((userName + ":" + password).getBytes());
    }

    public static String upperCaseSpreate(String cpt){
        String new_string = "";
        for (int i=0; i < cpt.length(); i++){
            char c = cpt.charAt(i);
            if(Character.isUpperCase(c)){
                new_string = new_string + " " + Character.toLowerCase(c);
            }
            else {
                new_string = new_string + c;
            }
        }
        return new_string;
    }
}
