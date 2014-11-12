/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.interpreter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import ru.fizteh.fivt.students.kalandarovshakarim.commands.Command;
import ru.fizteh.fivt.students.kalandarovshakarim.commands.filesystem.CommandParser;

/**
 *
 * @author Shakarim
 */
public class Interpreter {

    private final Map<String, Command> supportedCmds;
    private final String[] args;
    private static final String PROMPT = "$ ";

    public Interpreter(Command[] commands, String[] args) {
        this.supportedCmds = new HashMap<>();
        for (Command cmd : commands) {
            this.supportedCmds.put(cmd.getName(), cmd);
        }
        this.args = args;
    }

    private int interactiveMode() {
        try (Scanner input = new Scanner(System.in)) {
            System.out.print(PROMPT);
            while (input.hasNextLine()) {
                String command = input.nextLine();
                processCommand(command);
                System.out.print(PROMPT);
            }
            System.out.println();
        }
        return 0;
    }

    private int batchMode() {
        String[] commands = CommandParser.parseArgs(args);
        for (String cmd : commands) {
            if (!processCommand(cmd)) {
                return 1;
            }
        }
        return 0;
    }

    private boolean processCommand(String command) {
        command = command.trim();
        if (command.length() > 0) {
            String cmdName = CommandParser.getCmdName(command);
            if (!supportedCmds.containsKey(cmdName)) {
                System.err.printf("'%s' Unknown command\n", cmdName);
                return false;
            }
            try {
                String[] params = CommandParser.getParams(command);
                boolean rec = CommandParser.isRecursive(command);
                int opt = (rec ? 1 : 0);
                int argsNum = supportedCmds.get(cmdName).getArgsNum();

                if (params.length != argsNum + opt) {
                    throw new IllegalArgumentException("Invalid number of arguments");
                }

                supportedCmds.get(cmdName).exec(params);
            } catch (FileNotFoundException | NoSuchFileException e) {
                String msg = "%s: %s: No such File or Directory\n";
                System.err.printf(msg, cmdName, e.getMessage());
                return false;
            } catch (AccessDeniedException e) {
                String msg = "Cannot perform: %s: %s: Access denied\n";
                System.err.printf(msg, cmdName, e.getMessage());
                return false;
            } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                System.err.printf("%s: %s\n", cmdName, e.getMessage());
                return false;
            }
        }
        return true;
    }

    private void terminate(int status) {
        try {
            String[] strStatus = (status == 0 ? new String[0] : new String[1]);
            supportedCmds.get("exit").exec(strStatus);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void exec() {
        int status;
        if (args.length == 0) {
            status = interactiveMode();
        } else {
            status = batchMode();
        }
        terminate(status);
    }
}
