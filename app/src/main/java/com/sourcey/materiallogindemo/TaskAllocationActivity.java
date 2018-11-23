package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Fragments.DialogFragment;
import com.sourcey.materiallogindemo.Helper.DBhelper;
import com.sourcey.materiallogindemo.Helper.User;
import com.sourcey.materiallogindemo.Helper.MyListAdapter;

import java.util.ArrayList;

public class TaskAllocationActivity extends AppCompatActivity {
    private static final String TAG = "CheckinActivity";

    Button _add_newtask;
    TableLayout table_taskalloc;
    TableRow tableRow_taskkalloc;
    TextView tbl_task;
    TextView tbl_owner;
    TextView tbl_status;
    TextView tbl_timeline;
    TextView tbl_priority;
    ListView listView;
    DBhelper myDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("myTag", "inside oncreate() in taskallocation activity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_allocation);

        _add_newtask = findViewById(R.id.btn_add_newtask);
        table_taskalloc = findViewById(R.id.table_taskalloc);
        tableRow_taskkalloc = findViewById(R.id.table_taskalloc_row);
        tbl_task = findViewById(R.id.tbl_task);
        tbl_owner = findViewById(R.id.tbl_owner);
        tbl_status = findViewById(R.id.tbl_status);
        tbl_timeline = findViewById(R.id.tbl_timeline);
        tbl_priority = findViewById(R.id.tbl_priority);

        listView = findViewById(R.id.records_view);

        myDB = new DBhelper(this);

      //  ArrayList<String> records = new ArrayList<>();
        ArrayList<User> records = new ArrayList<>();

        Cursor data = myDB.getListContents_TA();

        if (data.getCount() == 0) {
            Toast.makeText(this, "No Tasks in DB !!", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                //records.add(data.getString(0));
                //records.add(data.getString(1));
                //records.add(data.getString(2));
                //records.add(data.getString(3));
                //records.add(data.getString(4));

                User user = new User(data.getString(0), data.getString(1),
                                     data.getString(2), data.getString(3),
                                     data.getString(4) );
                records.add(user);
                //  ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
                MyListAdapter listAdapter = new MyListAdapter  (this, R.layout.simple_list_item_5, records);

                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int row = position+1;
                Object item = parent.getItemAtPosition(position);

                User data = (User) item;
                String selected_task_name = data.getTask_name();
                String selected_task_owner = data.getTask_owner();
                String selected_task_status = data.getTask_status();
                String selected_task_timeline = data.getTask_timeline();
                String selected_task_priority = data.getTask_priority();
                showToast(row, selected_task_name);

                Bundle bundle = new Bundle();
                bundle.putString("frag_task_name", selected_task_name);
                bundle.putString("frag_task_owner", selected_task_owner);
                bundle.putString("frag_task_status", selected_task_status);
                bundle.putString("frag_task_timeline", selected_task_timeline);
                bundle.putString("frag_task_priority", selected_task_priority);

                showfragment(bundle);
                return false;
            }
        });

        _add_newtask.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addTask();
            }
        });

    }

    private void showfragment(Bundle bundle) {

        Log.d("myTag", "showfragment in taskallocation activity");

        FragmentManager fragmentManager = getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DialogFragment fragment = new DialogFragment();

        fragment.setArguments(bundle);

        fragment.show(fragmentManager, "fragment1");

    }

    private void addTask() {
        Log.d("myTag", "add task in taskallocation activity");
        startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void onBackPressed() {
        Log.d("myTag", "inside onbackpressed in taskallocation activity");

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void showToast(int row, String selected_task_name) {
        Toast.makeText(this, "You have selected item " +row+ " : " + "Task Name: " + selected_task_name, Toast.LENGTH_SHORT).show();
    }
}
