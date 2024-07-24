package com.vigilfuoco.mgr.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.vigilfuoco.mgr.MgrApplication;

//PER PROPERTIES EMAIL
public class PropertiesReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream inputStream = MgrApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                System.out.println("Properties file not found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}