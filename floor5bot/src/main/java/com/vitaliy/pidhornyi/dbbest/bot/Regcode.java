package com.vitaliy.pidhornyi.dbbest.bot;

public enum Regcode {
    REPLACE("->");
    private String value;

    Regcode(String value) {
        this.value = value;
    }

    public static Regcode getRegcode(String value) {
        if (value.equals(REPLACE.value)) {
            return REPLACE;
        }
        return null;
    }

    public static boolean isRegular(String key) {
        if (key.substring(0, 3).equals("RM#")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmoji(String key) {
        if (key.substring(0, 3).equals("EM#")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isPersonalRegular(String key) {
        if (key.substring(0, 3).equals("PR#")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.value;
    }
}
