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
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    boolean first = false;
    int id_to_update = -1;
    Intent gi;
    Switch switch1;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;
    TextView textView8;
    Button Student_save;
    EditText StudentName;
    EditText StudentAddress;
    EditText StudentPhone;
    EditText HomePhone;
    EditText FatherName;
    EditText FatherPhone;
    EditText MotherName;
    EditText MotherPhone;
    SQLiteDatabase db;
    HelperDB hlp;
    @Override
    protected void onStart() {
        super.onStart();
        first = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);
        textView8 = findViewById(R.id.textView8);
        Student_save = findViewById(R.id.go_back);
        StudentName = findViewById(R.id.StudentName);
        StudentAddress = findViewById(R.id.StudentAddress);
        StudentPhone = findViewById(R.id.StudentPhone);
        HomePhone = findViewById(R.id.HomePhone);
        FatherName = findViewById(R.id.FatherName);
        FatherPhone = findViewById(R.id.FatherPhone);
        MotherName = findViewById(R.id.MotherName);
        MotherPhone = findViewById(R.id.MotherPhone);
        switch1 = findViewById(R.id.switch1);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        read_from_intent();
    }


    public void read_from_intent()
    {
        if(!first){
            gi = getIntent();
            id_to_update = gi.getIntExtra("Key_id",-1);
            if(id_to_update != -1)
            {
                db = hlp.getWritableDatabase();
                Cursor crsr = db.query(Student.TABLE_STUDENTS, null, null, null, null, null, null);
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
                while (!crsr.isAfterLast()) {
                    int key_id = crsr.getInt(col1);
                    if(key_id==id_to_update)
                    {
                        StudentName.setText(crsr.getString(col2));
                        switch1.setChecked(crsr.getInt(col3)!=0);
                        StudentAddress.setText(crsr.getString(col4));
                        StudentPhone.setText(crsr.getString(col5));
                        HomePhone.setText(crsr.getString(col6));
                        FatherName.setText(crsr.getString(col7));
                        FatherPhone.setText(crsr.getString(col8));
                        MotherName.setText(crsr.getString(col9));
                        MotherPhone.setText(crsr.getString(col10));
                    }
                    crsr.moveToNext();
                }
                crsr.close();
                db.close();
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
            return true;
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

    public int toInt(boolean b)
    {
        if(b)
        {
            return 1;
        }
        return 0;
    }

    public void add_dataBase(String student_name, String student_address , String student_phone ,
    String home_phone, String father_name , String father_phone , String mother_name, String mother_phone)
    {
        ContentValues cv = new ContentValues();
        cv.put(Student.STUDENT_NAME , student_name);
        cv.put(Student.STATUS , Integer.toString(toInt(switch1.isChecked())));
        cv.put(Student.STUDENT_ADDRESS , student_address);
        cv.put(Student.STUDENT_PHONE , student_phone);
        cv.put(Student.HOME_PHONE , home_phone);
        cv.put(Student.FATHER_NAME , father_name);
        cv.put(Student.FATHER_PHONE , father_phone);
        cv.put(Student.MOTHER_NAME , mother_name);
        cv.put(Student.MOTHER_PHONE , mother_phone);
        db = hlp.getWritableDatabase();
        db.insert(Student.TABLE_STUDENTS, null, cv);
        db.close();
    }


    public void update_database(String student_name, String student_address , String student_phone ,
                             String home_phone, String father_name , String father_phone , String mother_name, String mother_phone)
    {
        ContentValues cv = new ContentValues();
        cv.put(Student.STUDENT_NAME , student_name);
        cv.put(Student.STATUS , Integer.toString(toInt(switch1.isChecked())));
        cv.put(Student.STUDENT_ADDRESS , student_address);
        cv.put(Student.STUDENT_PHONE , student_phone);
        cv.put(Student.HOME_PHONE , home_phone);
        cv.put(Student.FATHER_NAME , father_name);
        cv.put(Student.FATHER_PHONE , father_phone);
        cv.put(Student.MOTHER_NAME , mother_name);
        cv.put(Student.MOTHER_PHONE , mother_phone);
        db = hlp.getWritableDatabase();
        db.update(Student.TABLE_STUDENTS, cv,Student.KEY_ID + "=?", new String[]{Integer.toString(id_to_update)});
        db.close();
    }


    public void Student_Save(View view) {
        if(id_to_update == -1) {
            add_dataBase(String.valueOf(StudentName.getText()), String.valueOf(StudentAddress.getText()),
                    String.valueOf(StudentPhone.getText()), String.valueOf(HomePhone.getText()),
                    String.valueOf(FatherName.getText()), String.valueOf(FatherPhone.getText()),
                    String.valueOf(MotherName.getText()), String.valueOf(MotherPhone.getText()));
        }
        else {
            update_database(String.valueOf(StudentName.getText()), String.valueOf(StudentAddress.getText()),
                    String.valueOf(StudentPhone.getText()), String.valueOf(HomePhone.getText()),
                    String.valueOf(FatherName.getText()), String.valueOf(FatherPhone.getText()),
                    String.valueOf(MotherName.getText()), String.valueOf(MotherPhone.getText()));
        }
    }
}