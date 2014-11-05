/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.filemap.commands;

import java.io.IOException;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableInfo;

/**
 *
 * @author shakarim
 */
public class CommitCommand extends AbstractTableCommand {

    public CommitCommand(TableInfo context) {
        super("commit", 0, context);
    }

    @Override
    protected void onActiveTable(Table activeTable, String[] args) throws IOException {
        int changes = activeTable.commit();
        System.out.println(changes);
    }
}
