package com.cai.framework.annotation.apt;

import java.lang.reflect.Field;

public class ProtocolUtil {
    public static String packageName = "com.cai.framework.annotation.apt";

    public static String getClassNameForPackage(String simpleName) {
        StringBuilder builder = new StringBuilder();
        builder.append(packageName);
        builder.append(".");
        builder.append(simpleName);
        return builder.toString();
    }


    public static String getValueFromClass(Class middleClass) {
        try {
            Field field = middleClass.getDeclaredField("value");
            return (String) field.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateMiddleClass(String middleClassName, String value) {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code from apt. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import java.lang.String;\n");
        builder.append("public class ").append(middleClassName);
        builder.append("{\n");
        builder.append("  public static String value=\"").append(value).append("\";\n");
        builder.append("}\n");
        return builder.toString();

    }

}
