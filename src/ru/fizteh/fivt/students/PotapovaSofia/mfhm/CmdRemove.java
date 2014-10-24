package ru.fizteh.fivt.students.PotapovaSofia.MultyFileHashMap;

import java.util.Vector;

public class CmdRemove implements Command {
    @Override
    public void execute(Vector<String> args, DataBase db) {
        if (args.size() < 2) {
            CommandParser.fewArgs("remove");
        } else if (args.size() > 2) {
            CommandParser.tooMuchArgs("remove");
        } else {
            if (db.currentTable == null) {
                System.out.println("no table");
                return;
            }
            String key = args.get(1);
            String value = db.currentTable.remove(key);
            if (value == null) {
                System.out.println("not found");
            } else {
                System.out.println("removed");
            }
        }
    }
}
