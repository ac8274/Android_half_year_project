package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddGradeActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    EditText students_grade;
    EditText year_fourth;
    EditText grade_type;
    EditText grade_subject;
    Button Grade_save;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent gi;
    int Student_id;
    int id_to_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_grade);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        students_grade = findViewById(R.id.students_grade);
        year_fourth = findViewById(R.id.year_fourth);
        grade_type = findViewById(R.id.grade_type);
        grade_subject = findViewById(R.id.grade_subject);
        Grade_save = findViewById(R.id.go_back);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        gi = getIntent();
        Student_id = gi.getIntExtra("Key_id",-1);
        id_to_update = gi.getIntExtra("Key_Id",-1);
        if(id_to_update != -1)
        {
            db = hlp.getWritableDatabase();
            Cursor crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
            int col1 = crsr.getColumnIndex(Grades.KEY_ID);
            int col3 = crsr.getColumnIndex(Grades.STUDENT_GRADE);
            int col4 = crsr.getColumnIndex(Grades.YEAR_FOURTH);
            int col5 = crsr.getColumnIndex(Grades.GRADE_TYPE);
            int col6 = crsr.getColumnIndex(Grades.GRADE_SUBJECT);
            crsr.moveToFirst();
            while (!crsr.isAfterLast())
            {
                int key_id = crsr.getInt(col1);
                if(key_id == id_to_update)
                {
                    students_grade.setText(crsr.getString(col3));
                    year_fourth.setText(crsr.getString(col4));
                    grade_type.setText(crsr.getString(col5));
                    grade_subject.setText(crsr.getString(col6));
                }
                crsr.moveToNext();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.Add_Student)
        {
            Intent si = new Intent(this, MainActivity.class);
            startActivity(si);
        }
        else if (id == R.id.Student_List)
        {
            Intent si = new Intent(this, Student_list.class);
            startActivity(si);
        }
        else if(id == R.id.Sort_Students) {
            Intent si = new Intent(this, StudentListSortedActivity.class);
            startActivity(si);
        }
        else
        {
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
        }
        return true;
    }


    public void grade_save(View view) {
        if (id_to_update == -1) {
            add_dataBase(String.valueOf(students_grade.getText()), String.valueOf(year_fourth.getText()),
                    String.valueOf(grade_type.getText()), String.valueOf(grade_subject.getText()));
        }
        else
        {
            update_database(String.valueOf(students_grade.getText()), String.valueOf(year_fourth.getText()),
                    String.valueOf(grade_type.getText()), String.valueOf(grade_subject.getText()));
        }
    }

    void update_database(String Student_grade, String Year_fourth , String Grade_type ,
                      String Grade_subject)
    {
        ContentValues cv = new ContentValues();
        cv.put(Grades.STUDENT_GRADE , Student_grade);
        cv.put(Grades.YEAR_FOURTH , Year_fourth);
        cv.put(Grades.GRADE_TYPE , Grade_type);
        cv.put(Grades.GRADE_SUBJECT , Grade_subject);
        db = hlp.getWritableDatabase();
        db.update(Grades.TABLE_GRADES, cv,Grades.KEY_ID+"=?", new String[]{Integer.toString(id_to_update)});
        db.close();
    }


    void add_dataBase(String Student_grade, String Year_fourth , String Grade_type ,
                      String Grade_subject)
    {
        ContentValues cv = new ContentValues();
        cv.put(Grades.STUDENT_ID , Integer.toString(Student_id));
        cv.put(Grades.STUDENT_GRADE , Student_grade);
        cv.put(Grades.YEAR_FOURTH , Year_fourth);
        cv.put(Grades.GRADE_TYPE , Grade_type);
        cv.put(Grades.GRADE_SUBJECT , Grade_subject);
        db = hlp.getWritableDatabase();
        db.insert(Grades.TABLE_GRADES, null, cv);
        db.close();
    }
}