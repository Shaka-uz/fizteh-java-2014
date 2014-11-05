/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.filemap.table;

import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.storage.structured.TableProvider;

/**
 *
 * @author shakarim
 */
public class TableInfo {

    private final TableProvider provider;
    private Table activeTable;

    public TableInfo(TableProvider provider, Table activeTable) {
        this.provider = provider;
        this.activeTable = activeTable;
    }

    public void setActiveTable(Table table) {
        this.activeTable = table;
    }

    public Table getActiveTable() {
        return activeTable;
    }

    public TableProvider getProvider() {
        return provider;
    }
}
