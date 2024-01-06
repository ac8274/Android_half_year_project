package com.example.androidproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student_list.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;
    public HelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate = "CREATE TABLE " + Student.TABLE_STUDENTS;
        strCreate += " (" + Student.KEY_ID +" INTEGER PRIMARY KEY,";
        strCreate += " "+ Student.STUDENT_NAME + " TEXT,";
        strCreate += " "+ Student.STATUS + " INTEGER,";
        strCreate += " "+ Student.STUDENT_ADDRESS + " TEXT,";
        strCreate += " "+ Student.STUDENT_PHONE + " TEXT,";
        strCreate += " "+ Student.HOME_PHONE + " TEXT,";
        strCreate += " "+ Student.FATHER_NAME + " TEXT,";
        strCreate += " "+ Student.FATHER_PHONE + " TEXT,";
        strCreate += " "+ Student.MOTHER_NAME + " TEXT,";
        strCreate += " "+ Student.MOTHER_PHONE + " TEXT";
        strCreate += ");";
        db.execSQL(strCreate);

        strCreate = "CREATE TABLE " + Grades.TABLE_GRADES;
        strCreate += " (" + Grades.KEY_ID + " INTEGER PRIMARY KEY,";
        strCreate += " " + Grades.STUDENT_ID + " INTEGER,";
        strCreate += " " + Grades.STUDENT_GRADE + " INTEGER,";
        strCreate += " " + Grades.YEAR_FOURTH + " TEXT,";
        strCreate += " " + Grades.GRADE_TYPE + " TEXT,";
        strCreate += " " + Grades.GRADE_SUBJECT + " TEXT";
        strCreate += ");";
        db.execSQL(strCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete = "DROP TABLE IF EXISTS "+Student.TABLE_STUDENTS;
        db.execSQL(strDelete);
        strDelete = "DROP TABLE IF EXISTS "+Grades.TABLE_GRADES;
        db.execSQL(strDelete);
    }
}
