/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim;

import ru.fizteh.fivt.students.kalandarovshakarim.commands.database.*;
import ru.fizteh.fivt.students.kalandarovshakarim.commands.table.*;
import java.io.IOException;
import ru.fizteh.fivt.students.kalandarovshakarim.database.DataBase;
import ru.fizteh.fivt.students.kalandarovshakarim.interpreter.Interpreter;
import ru.fizteh.fivt.students.kalandarovshakarim.commands.Command;

/**
 *
 * @author shakarim
 */
public class Main {

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

        Interpreter interpreter = new Interpreter(commands, args);
        interpreter.exec();
    }
}
