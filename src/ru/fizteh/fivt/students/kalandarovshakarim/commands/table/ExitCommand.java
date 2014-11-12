/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.commands.table;

import java.io.IOException;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.table.TableInfo;
import ru.fizteh.fivt.students.kalandarovshakarim.commands.AbstractExit;

/**
 *
 * @author shakarim
 */
public class ExitCommand extends AbstractExit<TableInfo> {

    public ExitCommand(TableInfo context) {
        super(context);
    }

    @Override
    protected void onExit() throws IOException {
        Table activeTable = context.getActiveTable();

        if (activeTable != null) {
            activeTable.commit();
        }
    }
}
