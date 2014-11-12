/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.commands.table;

import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.table.TableInfo;

/**
 *
 * @author shakarim
 */
public class RollbackCommand extends AbstractTableCommand {

    public RollbackCommand(TableInfo context) {
        super("rollback", 0, context);
    }

    @Override
    protected void onActiveTable(Table activeTable, String[] args) {
        int changes = activeTable.rollback();
        System.out.println(changes);
    }
}
