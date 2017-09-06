package com.example.user.ormliteapplication;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

/**
 * Created by user on 9/6/17.
 */

class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context _context;
    private static final String DATABASE_NAME = "ormliteandroid.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Person, String> simpleDao = null;
    private RuntimeExceptionDao<Person, String> simpleRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }

    public Dao<Person, String> getDao() throws java.sql.SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(Person.class);
        }
        return simpleDao;
    }

    public RuntimeExceptionDao<Person, String> getSimpleDataDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(Person.class);
        }
        return simpleRuntimeDao;
    }

    //method for list of person
    public List<Person> GetData()
    {
        DatabaseHelper helper = new DatabaseHelper(_context);
        RuntimeExceptionDao<Person, String> simpleDao = helper.getSimpleDataDao();
        List<Person> list = simpleDao.queryForAll();
        return list;
    }

    //method for insert data
    public int addData(Person person)
    {
        RuntimeExceptionDao<Person, String> dao = getSimpleDataDao();
        int i = dao.create(person);
        return i;
    }

    //method for delete all rows
    public void deleteAll()
    {
        RuntimeExceptionDao<Person, String> dao = getSimpleDataDao();
        List<Person> list = dao.queryForAll();
        dao.delete(list);
    }

    @Override
    public void close() {
        super.close();
        simpleRuntimeDao = null;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Person.class);
        }catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Person.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (java.sql.SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
