package com.vitaliy.pidhornyi.dbbest.bot;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    private static Logger logger = Logger.getLogger(PropertiesReader.class);
    private static Properties properties;

    /**
     * Method which read all properties from file
     *
     * @return all properties from file
     */
    public static synchronized Properties readAllProperties() {
        if (properties == null) {
            properties = new Properties();
            InputStream input = null;
            try {
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();
                input = classloader.getResourceAsStream("config.properties");
                properties.load(input);
            } catch (IOException e) {
                logger.error(e.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        return properties;
    }
}
