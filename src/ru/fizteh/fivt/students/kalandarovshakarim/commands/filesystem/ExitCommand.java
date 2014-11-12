/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.commands.filesystem;

import ru.fizteh.fivt.students.kalandarovshakarim.commands.AbstractExit;
import ru.fizteh.fivt.students.kalandarovshakarim.interpreter.ShellUtils;

/**
 *
 * @author Shakarim
 */
public class ExitCommand extends AbstractExit<ShellUtils> {

    public ExitCommand(ShellUtils context) {
        super(context);
    }

    @Override
    protected void onExit() {
        // Nothing to do before exit.
    }
}
