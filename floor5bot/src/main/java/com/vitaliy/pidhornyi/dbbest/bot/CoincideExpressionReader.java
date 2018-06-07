package com.vitaliy.pidhornyi.dbbest.bot;

import org.apache.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class CoincideExpressionReader {
    private static Logger logger = Logger.getLogger(CoincideExpressionReader.class);
    private static Properties expressions;

    /**
     * Method which read all expressions from file
     *
     * @return all properties from file
     */
    public static synchronized Properties readAllExpressions() {

        expressions = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(new File(PropertiesReader.readAllProperties().getProperty("COINCIDE_FILE_PATH")));
            expressions.load(new InputStreamReader(input, StandardCharsets.UTF_8));
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

        return expressions;
    }
}
