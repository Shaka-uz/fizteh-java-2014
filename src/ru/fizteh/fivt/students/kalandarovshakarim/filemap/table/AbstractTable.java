/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.filemap.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.storeable.Utility;

/**
 *
 * @author shakarim
 */
public abstract class AbstractTable implements Table {

    protected Map<String, Storeable> table = new HashMap<>();
    protected Map<String, Storeable> oldData = new HashMap<>();
    private final String tableName;
    private final Class<?>[] tableSignature;

    public AbstractTable(String tableName, String[] tableSignature) {
        this.tableName = tableName;
        this.tableSignature = Utility.getSignature(tableSignature);
    }

    @Override
    public String getName() {
        return tableName;
    }

    @Override
    public Storeable get(String key) {
        checkArgument(key);
        return table.get(key);
    }

    @Override
    public Storeable put(String key, Storeable value) throws ColumnFormatException {
        checkArgument(value);

        if (table.containsKey(key) && value.equals(table.get(key))) {
            // If same value was overwritten.
            return value;
        }

        Storeable oldValue = table.put(key, value);
        if (!oldData.containsKey(key)) {
            // Value in key is changed first time.
            oldData.put(key, oldValue);
        } else if (value.equals(oldData.get(key))) {
            // If overwritten value matches first commited value.
            oldData.remove(key);
        }
        return oldValue;
    }

    @Override
    public Storeable remove(String key) {
        checkArgument(key);
        if (!table.containsKey(key)) {
            return null;
        }

        Storeable oldValue = table.remove(key);

        if (oldData.containsKey(key) && oldData.get(key) == null) {
            // If (key,value) which wasn't in first committed state removed.
            oldData.remove(key);
        } else if (!oldData.containsKey(key)) {
            // If (key,value) which was in first committed state removed.
            oldData.put(key, oldValue);
        }
        return oldValue;
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public int commit() throws IOException {
        int uncommited = oldData.size();
        if (uncommited == 0) {
            return 0;
        }

        save();
        oldData.clear();
        return uncommited;
    }

    @Override
    public int rollback() {
        for (Entry<String, Storeable> entry : oldData.entrySet()) {
            if (entry.getValue() != null) {
                table.put(entry.getKey(), entry.getValue());
            } else {
                table.remove(entry.getKey());
            }
        }
        int uncommited = oldData.size();
        oldData.clear();
        return uncommited;
    }

    @Override
    public int getColumnsCount() {
        return tableSignature.length;
    }

    @Override
    public Class<?> getColumnType(int columnIndex) throws IndexOutOfBoundsException {
        return tableSignature[columnIndex];
    }

    public List<String> list() {
        List<String> retVal = new ArrayList<>();
        for (Entry<String, Storeable> entry : table.entrySet()) {
            retVal.add(entry.getKey());
        }
        return retVal;
    }

    public int changes() {
        return oldData.size();
    }

    protected abstract void save() throws IOException;

    void checkArgument(Object argument) {
        if (argument == null) {
            throw new IllegalArgumentException("null");
        }
    }
}
