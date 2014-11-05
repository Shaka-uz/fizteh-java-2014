/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap.commands;

import java.io.IOException;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap.database.DataBase;
import ru.fizteh.fivt.students.kalandarovshakarim.shell.commands.AbstractCommand;

/**
 *
 * @author shakarim
 */
public class CreateCommand extends AbstractCommand<DataBase> {

    public CreateCommand(DataBase context) {
        super("create", 1, context);
    }

    @Override
    public void exec(String[] args) throws IOException {
        Table table = context.getProvider().createTable(args[0], null);
        if (table != null) {
            System.out.println("created");
        } else {
            System.out.printf("%s exists", args[0]);
        }
    }
}
