package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


import java.util.ArrayList;


public class StudentListSortedActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Cursor crsr;
    ListView Sorted_Students_List;
    Spinner spinner;
    Spinner spinner2;
    SQLiteDatabase db;
    HelperDB hlp;
    Intent gi;
    ArrayAdapter adp2;
    ArrayAdapter adp;
    ArrayList<String> tbl = new ArrayList<>();
    ArrayList<String> tbl2 = new ArrayList<>();
    ArrayList<String> ints = new ArrayList<>();
    String Choises[] = {"Quarter", "Subject"};
    int started = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_sorted);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        gi = getIntent();
        Sorted_Students_List = findViewById(R.id.Sorted_Students_List);
        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        Quarter_setter();
        set_quater(tbl2.get(0));
        Sorted_Students_List.setAdapter(adp);
        ArrayAdapter<String> ade = new ArrayAdapter<String>(this,
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item, Choises);
        spinner.setAdapter(ade);
        spinner2.setAdapter(adp2);
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        Quarter_setter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Add_Student) {
            Intent si = new Intent(this, MainActivity.class);
            startActivity(si);
        } else if (id == R.id.Student_List) {
            Intent si = new Intent(this, Student_list.class);
            startActivity(si);
        } else if (id == R.id.Sort_Students) {
            return true;
        }
        else
        {
            Intent si = new Intent(this, Credits.class);
            startActivity(si);
        }
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (R.id.spinner == parent.getId()) {
            if (position == 0) {
                Quarter_setter();
                set_quater(tbl2.get(0));
                started = 0;
            } else if (position == 1) {
                Subject_setter();
                set_subject(tbl2.get(0));
                started = 1;
            }
        } else if (R.id.spinner2 == parent.getId()) {
            if(started ==0)
            {
                set_quater(tbl2.get(position));
            }
            else if (started == 1)
            {
                set_subject(tbl2.get(position));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void set_quater(String quater)
    {
        String Table = Grades.TABLE_GRADES;
        String[] columns = {Grades.YEAR_FOURTH, Grades.STUDENT_GRADE};
        String selection = Grades.YEAR_FOURTH+"=?";
        String[] selectionArgs = {quater};
        String groupBy = null;
        String having = null;
        String orderBy = Grades.STUDENT_GRADE;
        String limit = null;
        tbl = new ArrayList<>();
        db= hlp.getWritableDatabase();
        crsr = db.query(Table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 =crsr.getColumnIndex(Grades.YEAR_FOURTH);
        int col2 = crsr.getColumnIndex(Grades.STUDENT_GRADE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String name = "Grade: "+crsr.getInt(col2)+ ", Quarter: "+crsr.getString(col1);
            tbl.add(name);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<>(StudentListSortedActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tbl);
        Sorted_Students_List.setAdapter(adp);
    }

    public void set_subject(String subject)
    {
        String Table = Grades.TABLE_GRADES;
        String[] columns = {Grades.GRADE_SUBJECT, Grades.STUDENT_GRADE};
        String selection = Grades.GRADE_SUBJECT+"=?";
        String[] selectionArgs = {subject};
        String groupBy = null;
        String having = null;
        String orderBy = Grades.STUDENT_GRADE;
        String limit = null;
        tbl = new ArrayList<>();
        db= hlp.getWritableDatabase();
        crsr = db.query(Table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 =crsr.getColumnIndex(Grades.GRADE_SUBJECT);
        int col2 = crsr.getColumnIndex(Grades.STUDENT_GRADE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String name = "Grade: "+crsr.getInt(col2)+ ", Subject: "+crsr.getString(col1);
            tbl.add(name);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<>(StudentListSortedActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tbl);
        Sorted_Students_List.setAdapter(adp);
    }



    public boolean checker(String name, int size)
    {
        boolean ans = true;
        for(int i =0;i < size; i++)
        {
            if(tbl2.get(i).equals(name))
            {
                ans = false;
            }
        }
        return ans;
    }

    public void Subject_setter()
    {
        String Table = Grades.TABLE_GRADES;
        String[] columns = {Grades.GRADE_SUBJECT};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        int array_size = 0;
        tbl2 = new ArrayList<>();
        db= hlp.getWritableDatabase();
        crsr = db.query(Table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 =crsr.getColumnIndex(Grades.GRADE_SUBJECT);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String name = crsr.getString(col1);
            if(checker(name,array_size))
            {
                tbl2.add(name);
                array_size++;
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp2 = new ArrayAdapter<>(StudentListSortedActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tbl2);
        spinner2.setAdapter(adp2);
    }


    public void Quarter_setter()
    {
        String Table = Grades.TABLE_GRADES;
        String[] columns = {Grades.YEAR_FOURTH};
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String limit = null;
        int array_size = 0;
        tbl2 = new ArrayList<>();
        db= hlp.getWritableDatabase();
        crsr = db.query(Table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        int col1 =crsr.getColumnIndex(Grades.YEAR_FOURTH);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String name = crsr.getString(col1);
            if(checker(name,array_size))
            {
                tbl2.add(name);
                array_size++;
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp2 = new ArrayAdapter<>(StudentListSortedActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tbl2);
        spinner2.setAdapter(adp2);
    }

}