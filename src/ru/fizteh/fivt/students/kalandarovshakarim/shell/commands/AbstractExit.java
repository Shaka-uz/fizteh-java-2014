/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.shell.commands;

import java.io.IOException;

/**
 *
 * @author Shakarim
 * @param <Type>
 */
public abstract class AbstractExit<Type> extends AbstractCommand<Type> {

    public AbstractExit(Type context) {
        super("exit", 0, context);
    }

    @Override
    public void exec(String[] args) throws IOException {
        onExit();
        System.exit(args.length);
    }

    protected abstract void onExit() throws IOException;
}
