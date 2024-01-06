package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Credits extends AppCompatActivity {
    TextView text1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        text1 = findViewById(R.id.textView10);
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
            Intent si = new Intent(this, StudentListSortedActivity.class);
            startActivity(si);
        }
        return true;
    }
}