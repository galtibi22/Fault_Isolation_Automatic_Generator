/*
package org.afeka.fi.backend.common;

import org.afeka.fi.backend.pojo.internal.ENV;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FiProperties extends FiCommon{
    private static FiProperties instance;
    private Properties prop = new Properties();

    */
/**
     * init method will load the fi.properties from the dist every time the method called
     * @return
     * @throws IOException
     *//*

    public static void init() throws Exception {
        instance = new FiProperties();
        instance.setSystemEnv();
        //String fiPropPath="C:\\Users\\eden.SPIDERSERVICES\\IdeaProjects\\Fault_Isolation_Automatic_Generator2\\fi_backend\\src\\main\\resources\\win.properties";
       // String fiPropPath=System.getProperty("user.dir")+"/fi_backend/src/main/resources/"+System.getProperty("env")+".properties";
        try {
            new FiLogger().called("loadProperties","fiProperties",System.getProperty("env")+".properties");
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(System.getProperty("env")+".properties");
            instance.prop.load(input);
            instance.loadProps();
            new FiLogger().info("fiProperties "+instance.prop);
        } catch (IOException io) {
            throw new IOException(String.format("Cannot find fi.properties in path=%s. The application cannot start without " +
                    "the "+System.getProperty("env")+".properties file"));
        }
    }


    private Object getProperty(String name){
            return instance.prop.get(name);

    }

    private void setSystemEnv() throws Exception {
        logger.info("start setSystemEnv method");
        String os=System.getProperty("os.name");
        if (os.contains(ENV.MAC))
            System.setProperty("env","mac");
        else if (os.contains(ENV.WIN))
            System.setProperty("env","win");
       else
           throw new Exception("Your os is not configure for the app. os "+os);


        logger.info("set env " +System.getProperty("env"));

    }

    //public static String DATA_PATH;
    //public static String RESOURCES_PATH;
   // public static String VIEW_DEMO_FITRE_PATH;
    //public static String VIEW_DEMO_FITRE_NAME;
    public static String ABBY_DLL_PATH;
    public static String ABBY_PROJECTID;
    public static String FI_GENERATOR_CLIENT_PATH;
    public static String FI_GENERATOR_MODE;
    public static String WEBAPP_PATH;
    public static String VIEW_DEMO_TRE_HTML_NAME="mainTre.html";
    public static String PYTHON_COMMAND_START;

    private void loadProps() throws IOException {
      //  DATA_PATH = instance.getProperty("data.path").toString();
        //RESOURCES_PATH=instance.getProperty("resources.path").toString();
      //  VIEW_DEMO_FITRE_PATH=instance.getProperty("viewdemo.fiTre.path").toString();
       // VIEW_DEMO_FITRE_NAME=instance.getProperty("viewdemo.fiTre.name").toString();
      //  ABBY_DLL_PATH=instance.getProperty("abby.dll.path").toString();
      //  ABBY_PROJECTID=instance.getProperty("abby.projectId").toString();
       // FI_GENERATOR_CLIENT_PATH=getProperty("fiGenerator.client.path").toString();
       // PYTHON_COMMAND_START =getProperty("fiGenerator.python.start").toString();

       // FI_GENERATOR_MODE= getProperty("fiGenerator.mode").toString();
       // WEBAPP_PATH=getProperty("webapp.path").toString();

    }


}
*/
