/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.filemap.commands;

import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableInfo;

/**
 *
 * @author shakarim
 */
public class PutCommand extends AbstractTableCommand {

    public PutCommand(TableInfo context) {
        super("put", 2, context);
    }

    @Override
    protected void onActiveTable(Table activeTable, String[] args) {
        Storeable value = null;
        Storeable oldValue = activeTable.put(args[0], value);

        if (oldValue == null) {
            System.out.println("new");
        } else {
            System.out.println("overwrite");
            System.out.println(oldValue);
        }
    }
}
