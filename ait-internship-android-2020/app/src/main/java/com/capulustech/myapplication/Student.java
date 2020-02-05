package com.capulustech.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "Student")
public class Student implements Serializable
{
    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField
    public String name;

    @DatabaseField
    public String usn;

    @DatabaseField
    public String branch;

    @DatabaseField
    public String section;

    @DatabaseField
    public String mobileNumber;


    private static DataBaseHelper dataBaseHelper;

    public static List<Student> getAllStudents(Context context)
    {
        try
        {
            dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
            Dao<Student, Long> studentDao = dataBaseHelper.getStudentDao();

            List<Student> students = studentDao.queryBuilder().orderBy("id",
                    false).query();
            /*ArrayList<Student> arrayList = new ArrayList<Student>();
            arrayList.addAll(students);*/

            OpenHelperManager.releaseHelper();
            return students;
        }
        catch (SQLException e)
        {
            Toast.makeText(context, "SQLException", Toast.LENGTH_SHORT).show();
            Log.e("nk", Log.getStackTraceString(e));
            return null;
        }
    }

    public static void addStudent(Context context, Student student)
    {
        try
        {
            dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
            Dao<Student, Long> studentDao = dataBaseHelper.getStudentDao();
            studentDao.createOrUpdate(student);
            OpenHelperManager.releaseHelper();
        }
        catch (SQLException e)
        {
            Toast.makeText(context, "SQLException", Toast.LENGTH_SHORT).show();
            Log.e("nk", Log.getStackTraceString(e));
        }
    }


    public static void deleteStudent(Context context, Student student)
    {
        try
        {
            dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
            Dao<Student, Long> studentDao = dataBaseHelper.getStudentDao();
            studentDao.delete(student);
            OpenHelperManager.releaseHelper();
        }
        catch (SQLException e)
        {
            Toast.makeText(context, "SQLException", Toast.LENGTH_SHORT).show();
            Log.e("nk", Log.getStackTraceString(e));
        }
    }


}