package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Student_list extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    int Item_Position = -1;
    SQLiteDatabase db;
    HelperDB hlp;
    ListView student_list;
    ArrayAdapter adp;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();

    ArrayList<Integer> ints = new ArrayList<>();
    Intent gi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        student_list = findViewById(R.id.student_list);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        Adapter_setter();
        student_list.setOnItemLongClickListener(this);
        student_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        registerForContextMenu(student_list);
        student_list.setAdapter(adp);
        gi  = getIntent();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Student info");
        menu.add("Edit student");
        menu.add("Student grades");
        menu.add("Add grades");
        menu.add("Remove student");
    }
    @Override
    public void onResume() {
        super.onResume();
        Adapter_setter();
        student_list.setAdapter(adp);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); // get extra info from adapter

        int position = menuinfo.position; // get the position of the item clicked in the adapter of the list view.


        String str = item.getTitle().toString(); // get option that the user choose.
        Intent si;
        if(str == "Student info"){
            si = new Intent(this,StudentInfoShowActivity.class);
            si.putExtra("Key_id",ints.get(Item_Position));
            startActivity(si);
        }
        else if(str == "Student grades"){
            si = new Intent(this, Grades_list.class);
            si.putExtra("Key_id",ints.get(Item_Position));
            startActivity(si);
        }
        else if (str == "Edit student"){
            si = new Intent(this,MainActivity.class);
            si.putExtra("Key_id",ints.get(Item_Position));
            startActivity(si);
        }
        else if (str == "Remove student")
        {
            db = hlp.getWritableDatabase();
            db.delete(Student.TABLE_STUDENTS, Student.KEY_ID+"=?", new String[]{Integer.toString(ints.get(Item_Position))});
            db.delete(Grades.TABLE_GRADES,Grades.STUDENT_ID+"=?",new String[]{Integer.toString(ints.get(Item_Position))});
            tbl.remove(Item_Position);
            ints.remove(Item_Position);
            adp.notifyDataSetChanged();

        }
        else
        {
            si = new Intent(this,AddGradeActivity.class);
            si.putExtra("Key_id",ints.get(Item_Position));
            startActivity(si);
        }
        return super.onContextItemSelected(item);
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
            si.putExtra("Key_id",-1);
            startActivity(si);
        }
        else if (id == R.id.Student_List)
        {
            return true;
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

    public void Adapter_setter()
    {
        db = hlp.getWritableDatabase();
        tbl = new ArrayList<>();
        ints = new ArrayList<>();

        crsr = db.query(Student.TABLE_STUDENTS, null, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Student.KEY_ID);
        int col2 = crsr.getColumnIndex(Student.STUDENT_NAME);
        int col5 = crsr.getColumnIndex(Student.STUDENT_PHONE);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            String name = crsr.getString(col2);
            String phone = crsr.getString(col5);
            int key_id = crsr.getInt(col1);
            String tmp = "Name: " + name + ", Phone: " + phone;
            tbl.add(tmp);
            ints.add(key_id);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<>(Student_list.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tbl);
    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Item_Position = position;
        return false;
    }
}