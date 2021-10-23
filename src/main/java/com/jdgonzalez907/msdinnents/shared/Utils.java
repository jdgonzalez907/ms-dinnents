package com.jdgonzalez907.msdinnents.shared;

public class Utils {
    public static Integer booleanToInteger(Boolean value) {
        return value ? 1 : 0;
    }

    public static Boolean integerToBoolean(Integer value) {
        return value > 0;
    }
}
