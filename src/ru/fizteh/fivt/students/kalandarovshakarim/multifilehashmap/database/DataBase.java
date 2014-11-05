/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.fizteh.fivt.students.kalandarovshakarim.multifilehashmap.database;

import java.io.IOException;
import ru.fizteh.fivt.students.kalandarovshakarim.filemap.table.TableInfo;

/**
 *
 * @author shakarim
 */
public class DataBase extends TableInfo {

    public DataBase(String pathToDataBase) throws IOException {
        super(new DataBaseProviderFactory().create(pathToDataBase), null);
    }
}
