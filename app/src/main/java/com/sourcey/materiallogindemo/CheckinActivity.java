package com.sourcey.materiallogindemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Helper.DBhelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckinActivity extends AppCompatActivity {
    private static final String TAG = "CheckinActivity";

    Button _checkin;
    TextView last_checkin_text;
    TextView last_checkin_value;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SHARED_PREFS_NAME = "name";
    public static final String SHARED_PREFS_CHECKEDIN_NAME = "checkedin_name";
    public static final String SHARED_PREFS_CHECKEDIN_TIME = "checkedin_time";


    public boolean checked_in = false;
    private String load_username;
    private String checkedin_time;
    String datetime;
    private String load_last_checkedin_time;

    DBhelper myDB;
    String new_team_member_name;
    String new_team_member_status;
    String new_team_member_status_time;
    String new_team_member_work_status;
    String new_team_member_task;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        loadData();

        Log.d("myTag", "inside oncreate() in checkin activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);

        _checkin = findViewById(R.id.btn_checkin);
        last_checkin_text = findViewById(R.id.last_checkin_text);
        last_checkin_value = findViewById(R.id.last_checkin_value);
        if (load_last_checkedin_time != null) {
            last_checkin_value.setText(load_last_checkedin_time);
        }

        _checkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getTime();
                addDatatoDB();
            }
        });
    }

    private void addDatatoDB() {
        new_team_member_name = load_username ;
        new_team_member_status = "CHECKED-IN" ;
        new_team_member_status_time = datetime;
        new_team_member_work_status = "FREE" ;
        new_team_member_task = "";

        myDB = new DBhelper(this);
        boolean insertData = myDB.addData_to_TS(new_team_member_name, new_team_member_status, new_team_member_status_time, new_team_member_work_status, new_team_member_task);

        if (insertData == true){
            Toast.makeText(this, "Check-In successful !!", Toast.LENGTH_SHORT).show();
        //    startActivity(new Intent(getApplicationContext(), TaskAllocationActivity.class));
        //    finish();
        //    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        } else {
            Toast.makeText(this, "Already Checked-In !!", Toast.LENGTH_SHORT).show();
        }

    }

    public void onBackPressed() {
        Log.d("myTag", "inside onbackpressed in checkin activity");

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    public void getTime() {
        Log.d("myTag", "inside showmessage() in checkin activity");

        checked_in = true;
        Date currentTime = Calendar.getInstance().getTime();

        // Formatting the fetched date to day month year ,and time in hh:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy, hh:mm:ss a");
        datetime = dateFormat.format(currentTime);

       // checkedin_time = currentTime.toString();
       // last_checkin_value.setText(checkedin_time);
        last_checkin_value.setText(datetime);

        saveData();

        Toast.makeText(this, "Check-In successful !!", Toast.LENGTH_SHORT).show();
    }

    public void saveData() {
        Log.d("myTag", "inside saveData() in checkin activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

     //   editor.putString(SHARED_PREFS_CHECKEDIN_NAME, load_username);
        editor.putString(SHARED_PREFS_CHECKEDIN_TIME, datetime);

        editor.apply();
    }

    public void loadData() {
        Log.d("myTag", "inside loadData() in checkin activity");

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        load_username = sharedPreferences.getString(SHARED_PREFS_NAME, "");
        load_last_checkedin_time = sharedPreferences.getString(SHARED_PREFS_CHECKEDIN_TIME, "");
    }

}