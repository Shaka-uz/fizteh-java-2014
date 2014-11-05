/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.storeable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.fizteh.fivt.storage.structured.ColumnFormatException;

/**
 *
 * @author shakarim
 */
public class Utility {

    private static final String EXCEPTION_FORMAT = "Column type is '%s', expected %s";
    private static final Map<String, Class<?>> types = new HashMap<String, Class<?>>() {
        {
            put("int", Integer.class);
            put("long", Long.class);
            put("byte", Byte.class);
            put("float", Float.class);
            put("double", Double.class);
            put("boolean", Boolean.class);
            put("String", String.class);
        }
    };

    public static Class<?> getClass(String typeName) {
        return types.get(typeName);
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

    public static String[] parseXML(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(xml.getBytes());
        Document document = builder.parse(stream);

        NodeList nodeList = document.getDocumentElement().getChildNodes();
        String[] list = new String[nodeList.getLength()];
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            int childCount = node.getChildNodes().getLength();
            boolean colTag = "col".equals(node.getNodeName());
            boolean nullTag = "null".equals(node.getNodeName());

            if (!colTag && !nullTag) {
                throw new Exception("Invalid tag name");
            } else if (nullTag && childCount > 0) {
                throw new Exception("invalid null tag");
            } else if (childCount > 1) {
                throw new Exception("Incorrect xml format");
            } else if (childCount == 1) {
                list[index] = node.getTextContent();
            } else if (!nullTag) {
                throw new Exception("Empty content");
            } else {
                list[index] = null;
            }
        }
        return list;
    }
}
