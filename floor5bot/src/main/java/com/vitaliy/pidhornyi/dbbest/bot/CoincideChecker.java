package com.vitaliy.pidhornyi.dbbest.bot;

import java.util.Enumeration;
import java.util.Properties;

public class CoincideChecker {
    Properties expressions;

    public String findAnswerForMessage(String message, String nameUser) {
        refreshExpressionsFromFile();
        Enumeration<String> enums = (Enumeration<String>) expressions.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = expressions.getProperty(key);
            if ((Regcode.isRegular(key)) && message.toLowerCase().matches(key.substring(3, key.length()))) {
                if (value.contains(Regcode.REPLACE.toString())) {
                    return message.toLowerCase().replace(value.split(Regcode.REPLACE.toString())[0], value.split(Regcode.REPLACE.toString())[1]);
                }
                return value;
            }
            if ((Regcode.isPersonalRegular(key))) {
                String username = key.substring(3, key.length()).split("#")[0];
                String regexpr = key.substring(3, key.length()).split("#")[1];
                if (username.equals(nameUser.replace(" ", "_"))
                    && message.toLowerCase().matches(regexpr)) {
                    if (value.contains(Regcode.REPLACE.toString())) {
                        return message.toLowerCase().replace(value.split(Regcode.REPLACE.toString())[0], value.split(Regcode.REPLACE.toString())[1]);
                    } else {
                        return value;
                    }
                }
            }
            if (message.equals(key)) {
                return value;
            }
        }
        return "";
    }

    public String findAnswerForStikker(String emoji) {
        refreshExpressionsFromFile();
        Enumeration<String> enums = (Enumeration<String>) expressions.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = expressions.getProperty(key);
            if (Regcode.isEmoji(key)) {
                return value;
            }
        }
        return "";
    }

    private void refreshExpressionsFromFile() {
        expressions = CoincideExpressionReader.readAllExpressions();
    }
}
