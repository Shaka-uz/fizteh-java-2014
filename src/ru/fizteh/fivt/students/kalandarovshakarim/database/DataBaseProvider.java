/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.database;

import ru.fizteh.fivt.students.kalandarovshakarim.table.MultiFileTable;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.storage.structured.TableProvider;
import ru.fizteh.fivt.students.kalandarovshakarim.interpreter.ShellUtils;
import ru.fizteh.fivt.students.kalandarovshakarim.table.TableRow;
import ru.fizteh.fivt.students.kalandarovshakarim.storeable.Utility;

/**
 *
 * @author shakarim
 */
public class DataBaseProvider implements TableProvider {

    private final Path directory;

    public DataBaseProvider(String directory) throws IOException {
        this.directory = Paths.get(directory);
        load();
    }

    @Override
    public Table getTable(String name) {
        checkTableName(name);

        if (Files.exists(directory.resolve(name))) {
            try {
                return new MultiFileTable(directory.toString(), name);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }

    @Override
    public Table createTable(String name, List<Class<?>> columnTypes) throws IOException {
        checkTableName(name);
        if (Files.exists(directory.resolve(name))) {
            return null;
        } else {
            return new MultiFileTable(directory.toString(), name);
        }
    }

    @Override
    public void removeTable(String name) throws IOException {
        checkTableName(name);

        if (!Files.exists(directory.resolve(name))) {
            throw new IllegalStateException(name + " not found");
        } else {
            ShellUtils utils = new ShellUtils();
            Path tablePath = directory.resolve(name);
            utils.rm(tablePath.toString(), true);
        }
    }

    @Override
    public Storeable deserialize(Table table, String value) throws ParseException {
        try {
            String[] contents = Utility.parseXML(value);
            Class<?>[] signature = new Class<?>[contents.length];

            for (int index = 0; index < table.getColumnsCount(); index++) {
                Object obj = table.getColumnType(index).
                        getConstructor(String.class).newInstance(contents[index]);
            }

        } catch (Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String serialize(Table table, Storeable value) throws ColumnFormatException {
        for (int columnIndex = 0; columnIndex < table.getColumnsCount(); columnIndex++) {
            Utility.checkClasses(value.getColumnAt(columnIndex).getClass(),
                    table.getColumnType(columnIndex));
        }
        return value.toString();
    }

    @Override
    public Storeable createFor(Table table) {
        Class<?>[] signature = new Class<?>[table.getColumnsCount()];
        for (int index = 0; index < signature.length; index++) {
            signature[index] = table.getColumnType(index);
        }
        return new TableRow(signature);
    }

    @Override
    public Storeable createFor(Table table, List<?> values)
            throws ColumnFormatException, IndexOutOfBoundsException {
        Storeable newRow = createFor(table);
        for (int index = 0; index < values.size(); index++) {
            Object get = values.get(index);
            newRow.setColumnAt(index, get);
        }
        try {
            newRow.setColumnAt(values.size(), null);
            throw new ColumnFormatException("Few number of columns");
        } catch (IndexOutOfBoundsException e) {
            return newRow;
        }
    }

    public List<String> listTables() throws IOException {
        List<String> list = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path table : directoryStream) {
                list.add(table.getFileName().toString());
            }
        }
        return list;
    }

    private void load() throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path pathToTable : directoryStream) {
                if (!Files.isDirectory(pathToTable)) {
                    String format = "'%s' contains non-directory files";
                    String eMessage = String.format(format, directory);
                    throw new IOException(eMessage);
                }
            }
        }
    }

    void checkTableName(String tableName) {
        if (tableName == null) {
            throw new IllegalArgumentException("Null cannot be an argument");
        }
        String[] invalidCharacters = {"|", "\\", "?", "*", "<", "\"", ":", ">", "/"};
        for (String character : invalidCharacters) {
            if (tableName.contains(character)) {
                throw new IllegalArgumentException("Table name contains invalid characters");
            }
        }
    }
}
