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

public class StudentInfoShowActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    TextView textView9;
    TextView IsACtive;
    TextView student_name_info;
    TextView student_address_info;
    TextView student_phone_info;
    TextView home_phone_info;
    TextView father_name_info;
    TextView father_phone_info;
    TextView mother_name_info;
    TextView mother_phone_info;
    Button go_back;
    int STudent_id;
    Cursor crsr;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent gi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_info_show);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        student_name_info = findViewById(R.id.student_name_info);
        student_address_info = findViewById(R.id.student_address_info);
        student_phone_info = findViewById(R.id.student_phone_info);
        home_phone_info = findViewById(R.id.home_phone_info);
        father_name_info = findViewById(R.id.father_name_info);
        father_phone_info = findViewById(R.id.father_phone_info);
        mother_name_info = findViewById(R.id.mother_name_info);
        mother_phone_info = findViewById(R.id.mother_phone_info);
        IsACtive = findViewById(R.id.IsACtive);
        go_back = findViewById(R.id.Go_back);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        gi = getIntent();
        STudent_id = gi.getIntExtra("Key_id",0);
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
            finish();
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

    public String switch_answear(int num)
    {
        if (num == 1)
        {
            return "Active";
        }
        return "Inactive";
    }


    public void Info_Set_up()
    {
        db = hlp.getWritableDatabase();

        crsr = db.query(Student.TABLE_STUDENTS, null, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Student.KEY_ID);
        int col2 = crsr.getColumnIndex(Student.STUDENT_NAME);
        int col3 = crsr.getColumnIndex(Student.STATUS);
        int col4 = crsr.getColumnIndex(Student.STUDENT_ADDRESS);
        int col5 = crsr.getColumnIndex(Student.STUDENT_PHONE);
        int col6 = crsr.getColumnIndex(Student.HOME_PHONE);
        int col7 = crsr.getColumnIndex(Student.FATHER_NAME);
        int col8 = crsr.getColumnIndex(Student.FATHER_PHONE);
        int col9 = crsr.getColumnIndex(Student.MOTHER_NAME);
        int col10 = crsr.getColumnIndex(Student.MOTHER_PHONE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int key_id = crsr.getInt(col1);
            if(key_id == STudent_id)
            {
                student_name_info.setText(crsr.getString(col2));
                IsACtive.setText(switch_answear(crsr.getInt(col3)));
                student_address_info.setText(crsr.getString(col4));
                student_phone_info.setText(crsr.getString(col5));
                home_phone_info.setText(crsr.getString(col6));
                father_name_info.setText(crsr.getString(col7));
                father_phone_info.setText(crsr.getString(col8));
                mother_name_info.setText(crsr.getString(col9));
                mother_phone_info.setText(crsr.getString(col10));
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
    }


    public void Back(View view) {
        finish();
    }
}