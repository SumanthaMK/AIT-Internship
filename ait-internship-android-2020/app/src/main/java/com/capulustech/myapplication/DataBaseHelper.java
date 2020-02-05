package com.capulustech.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper
{
    public static final String DATABASE_PATH = "/data/data/" + BuildConfig.APPLICATION_ID + "/databases/";//Buildconfig.appid is name of package
    private static final String DATABASE_NAME = "internship.db";
    private static final int DATABASE_VERSION = 1;

    Context context;
    private Dao<Student, Long> studentDao = null;//Data Access Object

    public DataBaseHelper(Context context)
    {
        super(context, DATABASE_PATH + DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource)
    {
        if (!sqLiteDatabase.isReadOnly())
        {
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }
        try
        {
            TableUtils.createTableIfNotExists(connectionSource, Student.class);

        }
        catch (SQLException e)
        {
            Log.e("nk", "Table Creation SQLException : \n" + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly())
        {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource,
                          int oldVersion, int newVersion)
    {

    }

    public Dao<Student, Long> getStudentDao() throws SQLException
    {
        try
        {
            if (studentDao == null)
            {
                studentDao = DaoManager.createDao(getConnectionSource(), Student.class);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return studentDao;
    }
}
