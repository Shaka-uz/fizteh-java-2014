/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.filemap.commands;

import java.io.IOException;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableInfo;
import ru.fizteh.fivt.students.kalandarovshakarim.shell.commands.AbstractCommand;

/**
 *
 * @author shakarim
 */
public abstract class AbstractTableCommand extends AbstractCommand<TableInfo> {

    public AbstractTableCommand(String name, int argNum, TableInfo context) {
        super(name, argNum, context);
    }

    @Override
    public void exec(String[] args) throws IOException {
        Table activeTable = context.getActiveTable();
        if (activeTable == null) {
            System.out.println("no table");
        } else {
            onActiveTable(activeTable, args);
        }
    }

    protected abstract void onActiveTable(Table activeTable, String[] args) throws IOException;
}
