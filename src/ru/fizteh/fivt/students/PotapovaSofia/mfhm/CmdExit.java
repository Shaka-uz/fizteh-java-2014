package ru.fizteh.fivt.students.PotapovaSofia.MultyFileHashMap;

import java.util.Vector;

public class CmdExit implements Command {
    @Override
    public void execute(Vector<String> args, DataBase db) {
        //db.writeToDataBase();
        System.exit(0);

    }
}
