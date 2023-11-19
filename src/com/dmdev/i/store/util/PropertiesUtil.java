package com.dmdev.i.store.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Необходимо выбрать тему, спроектировать схему базы данных для нее
 * и написать DAO с как минимум основными CRUD операциями для каждой сущности.
 */

public class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesUtil(){
    }

    public static String get(String key){
        return PROPERTIES.getProperty(key);
    }
    private static void loadProperties() {
        try (InputStream inputStream = PropertiesUtil.class.getClassLoader().
                getResourceAsStream("application.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
