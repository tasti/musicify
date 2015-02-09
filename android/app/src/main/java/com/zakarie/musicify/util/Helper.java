package com.zakarie.musicify.util;

public class Helper {

    public static boolean isValidUsername(String username) {
        return !username.isEmpty() && !username.contains(" ");
    }

}
