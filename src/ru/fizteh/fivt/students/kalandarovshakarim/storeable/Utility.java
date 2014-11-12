/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.storeable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.fizteh.fivt.storage.structured.ColumnFormatException;

/**
 *
 * @author shakarim
 */
public class Utility {

    //static final long serialVersionUID = 2L;
    private static final String EXCEPTION_FORMAT = "Column type is '%s', expected %s";
    private static final Map<String, Class<?>> TYPES = new HashMap<>();

    static {
        TYPES.put("int", Integer.class);
        TYPES.put("long", Long.class);
        TYPES.put("byte", Byte.class);
        TYPES.put("float", Float.class);
        TYPES.put("double", Double.class);
        TYPES.put("boolean", Boolean.class);
        TYPES.put("String", String.class);
    }

    public static Class<?> getClass(String typeName) {
        return TYPES.get(typeName);
    }

    public static Class<?>[] getSignature(String[] signature) {
        List<Class<?>> classes = new ArrayList<>();
        for (String type : signature) {
            classes.add(getClass(type));
        }
        return classes.toArray(new Class<?>[classes.size()]);
    }

    public static void checkClasses(Class<?> found, Class<?> expected)
            throws ColumnFormatException {
        if (!expected.equals(found)) {
            String message = String.format(EXCEPTION_FORMAT,
                    found.getSimpleName(), expected.getSimpleName());
            throw new ColumnFormatException(message);
        }
    }

    public static String[] parseXML(String xml) {
        String[] list = new String[]{};
        return list;
    }
}
