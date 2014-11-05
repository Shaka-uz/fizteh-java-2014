/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.storeable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableReader;

/**
 *
 * @author shakarim
 */
public class StructuredTable implements Table, AutoCloseable {

    private static final int DIRECTORIES_NUMBER = 16;
    private static final int FILES_NUMBER = 16;
    private final Path directoryPath;
    private final String tableName;
    private final String exceptionFormat = "Column type at %d index is '%s': Expected %s";
    private Map<String, Storeable> table;
    private String[] tableSignature;

    public StructuredTable(String tableDirectory, String tableName) throws IOException {
        this.directoryPath = Paths.get(tableDirectory);
        this.tableName = tableName;
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        } else {
            load();
        }
    }

    @Override
    public Storeable put(String key, Storeable value) throws ColumnFormatException {
        checkArgument(key);
        checkArgument(value);

        for (int columnIndex = 0; columnIndex < tableSignature.length; ++columnIndex) {
            String columnClassName = value.getColumnAt(columnIndex).getClass().getSimpleName();
            if (!tableSignature[columnIndex].equals(columnClassName)) {
                String exceptionMessage
                        = String.format(exceptionFormat,
                                columnIndex, columnClassName, tableSignature[columnIndex]);

                throw new ColumnFormatException(exceptionMessage);
            }
        }

        try {
            value.getColumnAt(tableSignature.length);
            throw new ColumnFormatException();
        } catch (IndexOutOfBoundsException e) {
            return table.put(key, value);
        }
    }

    @Override
    public Storeable remove(String key) {
        checkArgument(key);
        return table.remove(key);
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public int commit() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int rollback() {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnsCount() {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Class<?> getColumnType(int columnIndex) throws IndexOutOfBoundsException {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Storeable get(String key) {
        checkArgument(key);
        throw new UnsupportedOperationException("Not supported yet.");
//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    void checkArgument(Object argument) {
        if (argument == null) {
            throw new IllegalArgumentException("null: Incorrect argument");
        }
    }

    private void load() throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directoryPath)) {
            for (Path file : directoryStream) {
                String fileName = file.getFileName().toString();
                if ("".equals(fileName)) {
                } else if (!Files.isDirectory(file)) {
                    String format = "%s: Table directory contains illegal files";
                    throw new IOException(String.format(format, getName()));
                }
                readDir(file);
            }
        }
    }

    private void readDir(Path directory) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                String fileName = path.toString();
                try (AbstractReader reader = new TableReader(fileName)) {
                    String key;
                    String value;

                    while (!reader.eof()) {
                        key = reader.read();
                        value = reader.read();

                        Path subDirectoryPath = getDirectoryPath(key);
                        Path filePath = getFilePath(key);

                        if (directory.compareTo(subDirectoryPath) != 0
                                || path.compareTo(filePath) != 0) {
                            String format = "%s: contains wrong key";
                            String eMassage = String.format(format, fileName);
                            throw new IOException(eMassage);
                        }
                        table.put(key, null);
                    }
                }
            }
        }
    }

    private Path getDirectoryPath(String key) {
        int hashCode = Math.abs(key.hashCode());
        int nDir = hashCode % DIRECTORIES_NUMBER;

        String directoryName = new StringBuilder().append(nDir).append(".dir").toString();
        return directoryPath.resolve(directoryName);
    }

    private Path getFilePath(String key) {
        int hashCode = Math.abs(key.hashCode());
        int nFile = hashCode / DIRECTORIES_NUMBER % FILES_NUMBER;

        String fileName = new StringBuilder().append(nFile).append(".dat").toString();
        return directoryPath.resolve(fileName);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *
 package ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap.database;

 import java.io.IOException;
 import java.nio.file.DirectoryStream;
 import java.nio.file.Files;
 import java.nio.file.Path;
 import java.nio.file.Paths;
 import java.util.Map.Entry;
 import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.AbstractTable;
 import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableReader;
 import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableWriter;
 import ru.fizteh.fivt.students.kalandarovshakarim.shell.ShellUtils;

 /**
 *
 * @author shakarim

 public class MultiFileTable extends AbstractTable {

 private static final int DIRECTORIES_NUMBER = 16;
 private static final int FILES_NUMBER = 16;
 private final Path directoryPath;

 public MultiFileTable(String directory, String tableName) throws IOException {
 super(tableName);
 directoryPath = Paths.get(directory, tableName);
 }

 @Override
 protected void save() throws IOException {
 ShellUtils utils = new ShellUtils();
 utils.rm(directoryPath.toString(), true);
 Files.createDirectory(directoryPath);

 TableWriter[][] writers = new TableWriter[DIRECTORIES_NUMBER][FILES_NUMBER];

 try {
 for (Entry<String, String> entry : table.entrySet()) {
 String key = entry.getKey();
 String value = entry.getValue();
 int hashCode = Math.abs(key.hashCode());
 int nDir = hashCode % DIRECTORIES_NUMBER;
 int nFile = hashCode / DIRECTORIES_NUMBER % FILES_NUMBER;

 Path subDirectoryPath = getDirectoryPath(key);
 Path filePath = getFilePath(key);

 if (!Files.exists(subDirectoryPath)) {
 Files.createDirectory(subDirectoryPath);
 }

 if (writers[nDir][nFile] == null) {
 writers[nDir][nFile] = new TableWriter(filePath.toString());
 }

 writers[nDir][nFile].write(key);
 writers[nDir][nFile].write(value);
 }
 } finally {
 for (int dir = 0; dir < DIRECTORIES_NUMBER; ++dir) {
 for (int file = 0; file < FILES_NUMBER; ++file) {
 if (writers[dir][file] != null) {
 writers[dir][file].close();
 }
 }
 }
 }
 }
 */
