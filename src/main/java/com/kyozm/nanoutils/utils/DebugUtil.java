package com.kyozm.nanoutils.utils;

public class DebugUtil {
    public String testString = "";
    public Float testFloat = 0.29f;
    public Integer testInt = 1;
    public Double testDouble = 1.0;
    public Boolean testBool = false;

    public static String getTestString() {
        return new DebugUtil().testString;
    }

    public static Float getTestFloat() {
        return new DebugUtil().testFloat;
    }

    public static Integer getTestInt() {
        return new DebugUtil().testInt;
    }

    public static Double getTestDouble() {
        return new DebugUtil().testDouble;
    }

    public static Boolean getTestBool() {
        return new DebugUtil().testBool;
    }

}
