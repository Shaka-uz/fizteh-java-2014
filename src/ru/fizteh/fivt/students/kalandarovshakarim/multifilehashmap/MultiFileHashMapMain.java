/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap;

import java.io.IOException;
import ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap.database.DataBase;
import ru.fizteh.fivt.students.kalandarovshakarim.shell.Shell;
import ru.fizteh.fivt.students.kalandarovshakarim.shell.commands.Command;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.commands.*;
import ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap.commands.*;

/**
 *
 * @author shakarim
 */
public class MultiFileHashMapMain {

    public static void main(String[] args) {
        DataBase dataBase = null;
        try {
            dataBase = new DataBase(System.getProperty("fizteh.db.dir"));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Command[] commands = new Command[]{
            new PutCommand(dataBase),
            new GetCommand(dataBase),
            new UseCommand(dataBase),
            new ListCommand(dataBase),
            new SizeCommand(dataBase),
            new ExitCommand(dataBase),
            new DropCommand(dataBase),
            new CreateCommand(dataBase),
            new RemoveCommand(dataBase),
            new CommitCommand(dataBase),
            new RollbackCommand(dataBase),
            new ShowTablesCommand(dataBase)
        };

        Shell shell = new Shell(commands, args);
        shell.exec();
    }
}
