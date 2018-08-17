package com.kanon.vintage.configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class propertiesLoader {
    private static propertiesLoader ourInstance = new propertiesLoader();

    public static propertiesLoader getInstance() {
        return ourInstance;
    }

    private static Properties properties = new Properties();

    private final static String propertiesName = "vintage.properties";

    static{
        URL profile =  Thread.currentThread().getContextClassLoader().getResource(propertiesName);
        try {
            properties.load(profile.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private propertiesLoader() { }

    public static String getProperty(String key){
        return properties.getProperty(key, null);
    }


}
