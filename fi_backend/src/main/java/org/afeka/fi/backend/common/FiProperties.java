package org.afeka.fi.backend.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FiProperties {
    private static FiProperties instance;
    private Properties prop = new Properties();

    /**
     * init method will load the fi.properties from the dist every time the method called
     * @return
     * @throws IOException
     */
    public static void init() throws IOException {
        String fiPropPath=System.getProperty("user.dir")+"/src/main/resources/fi.properties";
        try {
            instance = new FiProperties();
            InputStream input = new FileInputStream(fiPropPath);
            instance.prop.load(input);
            instance.loadProps();
        } catch (IOException io) {
            throw new IOException(String.format("Cannot find fi.properties in path=%s. The application cannot start without " +
                    "the fi.properties file", fiPropPath));
        }
    }


    private Object getProperty(String name){
            return instance.prop.get(name);

    }

    public static String DATA_PATH;
    public static String RESOURCES_PATH;
    public static String VIEW_DEMO_FITRE_PATH;
    public static String VIEW_DEMO_FITRE_NAME;

    private void loadProps() throws IOException {
        DATA_PATH = instance.getProperty("data.path").toString();
        RESOURCES_PATH=instance.getProperty("resources.path").toString();
        VIEW_DEMO_FITRE_PATH=instance.getProperty("viewdemo.fiTre.path").toString();
        VIEW_DEMO_FITRE_NAME=instance.getProperty("viewdemo.fiTre.name").toString();

    }

}
