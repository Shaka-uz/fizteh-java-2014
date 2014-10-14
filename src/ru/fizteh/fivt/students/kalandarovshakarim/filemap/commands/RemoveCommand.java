/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.filemap.commands;

import java.io.IOException;
import ru.fizteh.fivt.storage.strings.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.OneTableBase;

/**
 *
 * @author shakarim
 */
public class RemoveCommand extends AbstractTableCommand {

    public RemoveCommand(OneTableBase context) {
        super("remove", 1, context);
    }

    @Override
    protected void onActiveTable(Table activeTable, String[] args) throws IOException {
        String deleted = activeTable.remove(args[0]);

        if (deleted == null) {
            throw new IOException("not found");
        } else {
            System.out.println("removed");
        }
    }
}
