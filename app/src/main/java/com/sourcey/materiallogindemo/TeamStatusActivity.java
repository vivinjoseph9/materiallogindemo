package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Helper.DBhelper;
import com.sourcey.materiallogindemo.Helper.MyListAdapter_TS;
import com.sourcey.materiallogindemo.Helper.TeamStatus;

import java.util.ArrayList;

public class TeamStatusActivity extends AppCompatActivity {
    private static final String TAG = "CheckinActivity";

    TextView text_name;
    TextView text_checked_in;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_PREFS_CHECKEDIN_NAME = "checkedin_name";
    public static final String SHARED_PREFS_CHECKEDIN_TIME = "checkedin_time";

    private String checkedin_name;
    private String checkedin_time;

    String new_team_member_name;
    String new_team_member_status;
    String new_team_member_status_time;
    String new_team_member_work_status;
    String new_team_member_task;

    ListView listView;
    DBhelper myDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //loadData();

        Log.d("myTag", "inside oncreate() in teamstatus activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_status_new);

        //text_name = findViewById(R.id.text_name);
        //text_checked_in = findViewById(R.id.text_checked_in);

        //text_name.setText(checkedin_name);
        //text_checked_in.setText(checkedin_time);

        listView = findViewById(R.id.records_view_ts);

        myDB = new DBhelper(this);

        ArrayList<TeamStatus> records = new ArrayList<>();

        Cursor data = myDB.getListContents_TS();

        if (data.getCount() == 0) {
            Toast.makeText(this, "No Tasks in DB !!", Toast.LENGTH_SHORT).show();
        } else {
            while (data.moveToNext()) {
                TeamStatus teamStatus = new TeamStatus(data.getString(0), data.getString(1),
                        data.getString(2), data.getString(3),
                        data.getString(4) );
                records.add(teamStatus);
                //  ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
                MyListAdapter_TS listAdapter = new MyListAdapter_TS  (this, R.layout.simple_list_item_5_ts, records);

                listView.setAdapter(listAdapter);
            }
        }

    }

    public void onBackPressed() {
        Log.d("myTag", "inside onbackpressed in teamstatus activity");

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void loadData() {
        Log.d("myTag", "inside loadData in teamstatus activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        checkedin_name = sharedPreferences.getString(SHARED_PREFS_CHECKEDIN_NAME, "");
        checkedin_time = sharedPreferences.getString(SHARED_PREFS_CHECKEDIN_TIME, "");

    }

}
