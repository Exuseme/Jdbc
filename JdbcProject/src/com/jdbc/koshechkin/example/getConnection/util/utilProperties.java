package com.jdbc.koshechkin.example.getConnection.util;

import java.io.IOException;
import java.util.Properties;

public class utilProperties {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadPropertiesFile();
    }

    public static String getKey(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadPropertiesFile() {
        try (var input = utilProperties.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
