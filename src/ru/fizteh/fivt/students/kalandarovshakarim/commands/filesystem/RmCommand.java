/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.commands.filesystem;

import ru.fizteh.fivt.students.kalandarovshakarim.commands.AbstractCommand;
import java.io.FileNotFoundException;
import java.io.IOException;
import ru.fizteh.fivt.students.kalandarovshakarim.interpreter.ShellUtils;

/**
 *
 * @author Shakarim
 */
public class RmCommand extends AbstractCommand<ShellUtils> {

    public RmCommand(ShellUtils context) {
        super("rm", 1, context);
    }

    @Override
    public void exec(String[] args) throws FileNotFoundException, IOException {
        context.rm(args[0], (args.length == 2));
    }
}
