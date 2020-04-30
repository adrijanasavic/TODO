package com.example.todo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.todo.db.model.Grupa;
import com.example.todo.db.model.Todo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "TODO.db";

    private static final int DATABASE_VERSION = 1;

    private Dao<Todo,Integer> todoDao = null;
    private Dao<Grupa, Integer> grupaDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Grupa.class);
            TableUtils.createTable(connectionSource, Todo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {


        try {
            TableUtils.dropTable(connectionSource, Grupa.class, true);
            TableUtils.dropTable(connectionSource, Todo.class, true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<Todo, Integer> getTodoDao() throws SQLException {
        if (todoDao == null) {
            todoDao = getDao(Todo.class);
        }

        return todoDao;
    }
    public Dao<Grupa, Integer> getGrupaDao() throws SQLException {
        if (grupaDao == null) {
            grupaDao = getDao(Grupa.class);
        }
        return grupaDao;
    }


    @Override
    public void close() {
        todoDao = null;
        grupaDao = null;

        super.close();
    }
}
