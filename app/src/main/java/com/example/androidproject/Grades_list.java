package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

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

public class Grades_list extends AppCompatActivity  implements AdapterView.OnItemLongClickListener {
    int Item_Position = -1;
    SQLiteDatabase db;
    HelperDB hlp;
    ListView grades_list;
    ArrayAdapter adp;
    Cursor crsr;
    ArrayList<String> tbl = new ArrayList<>();

    ArrayList<Integer> ints = new ArrayList<>();
    int student_id;
    Intent gi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_grades_info);
        grades_list = findViewById(R.id.grades_list);
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();
        gi  = getIntent();
        student_id = gi.getIntExtra("Key_id",0);
        Adapter_setter();
        grades_list.setOnItemLongClickListener(this);
        grades_list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        registerForContextMenu(grades_list);
        grades_list.setAdapter(adp);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        menu.add("Grade info");
        menu.add("Remove Grade");
        menu.add("Edit Grade");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo(); // get extra info from adapter

        int position = menuinfo.position; // get the position of the item clicked in the adapter of the list view.


        String str = item.getTitle().toString(); // get option that the user choose.
        Intent si;
        if(str == "Grade info"){
            si = new Intent(this,GradesInfoListActivity.class);
            si.putExtra("Key_id",ints.get(Item_Position));
            startActivity(si);
        }
        else if (str == "Remove Grade"){
            db = hlp.getWritableDatabase();
            db.delete(Grades.TABLE_GRADES,Grades.KEY_ID+"=?", new String[]{Integer.toString(ints.get(Item_Position))});
            db.close();
            tbl.remove(Item_Position);
            ints.remove(Item_Position);
            adp.notifyDataSetChanged();
        }
        else
        {
            si = new Intent(this,AddGradeActivity.class);
            si.putExtra("Key_Id",ints.get(Item_Position));
            startActivity(si);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Adapter_setter();
        grades_list.setAdapter(adp);
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
            Intent si = new Intent(this,MainActivity.class);
            startActivity(si);
            return true;
        }
        else if (id == R.id.Student_List)
        {
            finish();
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

        crsr = db.query(Grades.TABLE_GRADES, null, null, null, null, null, null);
        int col1 = crsr.getColumnIndex(Grades.KEY_ID);
        int col2 = crsr.getColumnIndex(Grades.STUDENT_ID);
        int col3 = crsr.getColumnIndex(Grades.STUDENT_GRADE);
        int col6 = crsr.getColumnIndex(Grades.GRADE_SUBJECT);

        crsr.moveToFirst();
        while (!crsr.isAfterLast())
        {
            int student_id_table = crsr.getInt(col2);
            String grade_subject = crsr.getString(col6);
            int grade = crsr.getInt(col3);
            int key_id = crsr.getInt(col1);
            if(student_id_table == student_id)
            {
                String tmp = "Subject: " + grade_subject + ", Grade: " + grade;
                tbl.add(tmp);
                ints.add(key_id);
            }
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        adp = new ArrayAdapter<>(Grades_list.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, tbl);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Item_Position = position;
        return false;
    }

}