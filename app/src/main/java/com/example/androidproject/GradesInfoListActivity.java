package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GradesInfoListActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView students_grade;
    TextView year_fourth;
    TextView grade_type;
    TextView grade_subject;
    Button go_back;
    int Key_id;
    Cursor crsr;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent gi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grades_info_list);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        students_grade = findViewById(R.id.students_grade);
        year_fourth = findViewById(R.id.year_fourth);
        grade_type = findViewById(R.id.grade_type);
        grade_subject = findViewById(R.id.grade_subject);
        go_back = findViewById(R.id.go_back);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        gi = getIntent();
        Key_id = gi.getIntExtra("Key_id",0);
        Info_Set_up();
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




    public void Info_Set_up()
    {
        db = hlp.getWritableDatabase();

        crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Grades.KEY_ID);
        int col3 = crsr.getColumnIndex(Grades.STUDENT_GRADE);
        int col4 = crsr.getColumnIndex(Grades.YEAR_FOURTH);
        int col5 = crsr.getColumnIndex(Grades.GRADE_TYPE);
        int col6 = crsr.getColumnIndex(Grades.GRADE_SUBJECT);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int key_id = crsr.getInt(col1);
            if(key_id == Key_id)
            {
                students_grade.setText(crsr.getString(col3));
                year_fourth.setText(crsr.getString(col4));
                grade_type.setText(crsr.getString(col5));
                grade_subject.setText(crsr.getString(col6));
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }

    public void go_Back(View view) {
        finish();
    }
}