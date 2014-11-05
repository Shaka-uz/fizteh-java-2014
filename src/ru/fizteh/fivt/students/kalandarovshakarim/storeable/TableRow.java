/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.storeable;

import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;

/**
 *
 * @author shakarim
 */
public class TableRow implements Storeable {

    private final Class<?>[] signature;
    private final Object[] rowObjects;

    public TableRow(Class<?>[] signature) {
        this.signature = signature;
        this.rowObjects = new Object[signature.length];
    }

    private void checkSignature(int columnIndex, Class<?> cls)
            throws ColumnFormatException, IndexOutOfBoundsException {
        Utility.checkClasses(cls, signature[columnIndex]);
    }

    @Override
    public void setColumnAt(int columnIndex, Object value)
            throws ColumnFormatException, IndexOutOfBoundsException {
        if (value != null) {
            checkSignature(columnIndex, value.getClass());
        }
        rowObjects[columnIndex] = value;
    }

    @Override
    public Object getColumnAt(int columnIndex) throws IndexOutOfBoundsException {
        return rowObjects[columnIndex];
    }

    @Override
    public Integer getIntAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, Integer.class);
        return (Integer) rowObjects[columnIndex];
    }

    @Override
    public Long getLongAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, Long.class);
        return (Long) rowObjects[columnIndex];
    }

    @Override
    public Byte getByteAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, Byte.class);
        return (Byte) rowObjects[columnIndex];
    }

    @Override
    public Float getFloatAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, Float.class);
        return (Float) rowObjects[columnIndex];
    }

    @Override
    public Double getDoubleAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, Double.class);
        return (Double) rowObjects[columnIndex];
    }

    @Override
    public Boolean getBooleanAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, Boolean.class);
        return (Boolean) rowObjects[columnIndex];
    }

    @Override
    public String getStringAt(int columnIndex)
            throws ColumnFormatException, IndexOutOfBoundsException {
        checkSignature(columnIndex, String.class);
        return (String) rowObjects[columnIndex];
    }

    @Override
    public String toString() {
        StringBuilder serializedValue = new StringBuilder("<row>");
        for (Object columnObject : rowObjects) {
            serializedValue.append("<col>");
            serializedValue.append(columnObject);
            serializedValue.append("</col>");
        }
        serializedValue.append("</row>");
        return serializedValue.toString();
    }
}
