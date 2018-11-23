package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Helper.DBhelper;

public class AddTaskActivity extends AppCompatActivity {

    Button _add;
    EditText task;
    EditText taskowner;
    EditText timeline;
    Spinner priority;
    DBhelper myDB;

    String new_task;
    String new_task_owner;
    String new_task_status;
    String new_task_timeline;
    String new_task_priority;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_newtask);

        _add = findViewById(R.id.btn_save_task);
        task = findViewById(R.id.input_task);
        taskowner = findViewById(R.id.input_task_owner);
        timeline = findViewById(R.id.input_timeline);
        priority =  findViewById(R.id.input_priority);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(AddTaskActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.priority_list));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        priority.setAdapter(myAdapter);

        _add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new_task = task.getText().toString() ;
                new_task_owner = taskowner.getText().toString() ;
                new_task_status = "In Progress";
                new_task_timeline = timeline.getText().toString() ;
                new_task_priority = priority.getSelectedItem().toString() ;

                add();
            }
        });

    }

    public void add() {
        myDB = new DBhelper(this);
        boolean insertData = myDB.addData_to_TA(new_task, new_task_owner, new_task_status, new_task_timeline, new_task_priority);

        if (insertData == true){
            Toast.makeText(this, "New Task Added !!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), TaskAllocationActivity.class));
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            Toast.makeText(this, "New Task could not be added !!", Toast.LENGTH_SHORT).show();
        }

        startActivity(new Intent(getApplicationContext(), TaskAllocationActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onBackPressed() {
        Log.d("myTag", "inside onbackpressed in taskallocation activity");
        startActivity(new Intent(getApplicationContext(), TaskAllocationActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}