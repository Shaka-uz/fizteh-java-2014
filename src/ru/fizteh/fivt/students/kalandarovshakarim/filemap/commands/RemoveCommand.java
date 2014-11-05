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
public class RemoveCommand extends AbstractTableCommand {

    public RemoveCommand(TableInfo context) {
        super("remove", 1, context);
    }

    @Override
    protected void onActiveTable(Table activeTable, String[] args) {
        Storeable deleted = activeTable.remove(args[0]);

        if (deleted == null) {
            System.out.println("not found");
        } else {
            System.out.println("removed");
        }
    }
}
