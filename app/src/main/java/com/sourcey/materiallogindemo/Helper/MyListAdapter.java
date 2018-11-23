package com.sourcey.materiallogindemo.Helper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sourcey.materiallogindemo.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyListAdapter extends ArrayAdapter<User> {

    private LayoutInflater mInflater;
    private ArrayList<User> users;
    private int mViewResourceId;
    String taskstatus_fromDB;
    String string_In_Progress = "In Progress";
    String string_DONE = "DONE";

    public MyListAdapter(Context context, int textViewResourceId, ArrayList<User> users) {
        super(context, textViewResourceId, users);
        this.users = users;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        User user = users.get(position);

        Date currentTime = Calendar.getInstance().getTime();

        // Formatting the fetched date to day month year ,and time in hh:mm:ss
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String datetime = dateFormat.format(currentTime);

        if (user != null) {
            TextView t1 = (TextView) convertView.findViewById(R.id.listview_text1);
            TextView t2 = (TextView) convertView.findViewById(R.id.listview_text2);
            TextView t3 = (TextView) convertView.findViewById(R.id.listview_text3);
            TextView t4 = (TextView) convertView.findViewById(R.id.listview_text4);
            TextView t5 = (TextView) convertView.findViewById(R.id.listview_text5);

            if (t1 != null) {
                t1.setText(user.getTask_name());
            }

            if (t2 != null) {
                t2.setText((user.getTask_owner()));
            }

            if (t3 != null) {
                t3.setText((user.getTask_status()));
                taskstatus_fromDB = (String) t3.getText();
            }

            if (t4 != null) {
                t4.setText((user.getTask_timeline()));

                /*
                Set the task text color to RED if the task is overdue (if the task time has exceeded the current time)
                Set the task text color to GREEN if the task is complete
                 */
                String time_fromDB = (String) t4.getText();
                if (datetime.compareTo(time_fromDB) > 0 & taskstatus_fromDB.equals(string_In_Progress)) {
                        t1.setTextColor(Color.RED);
                        t2.setTextColor(Color.RED);
                        t3.setTextColor(Color.RED);
                        t4.setTextColor(Color.RED);
                        t5.setTextColor(Color.RED);
                } else if (taskstatus_fromDB.equals(string_DONE)) {
                        t1.setTextColor(Color.parseColor("#228B22"));
                        t2.setTextColor(Color.parseColor("#228B22"));
                        t3.setTextColor(Color.parseColor("#228B22"));
                        t4.setTextColor(Color.parseColor("#228B22"));
                        t5.setTextColor(Color.parseColor("#228B22"));
                }
                else {
                    t4.setTextColor(-1979711488);
                }
            }

            if (t5 != null) {
                t5.setText((user.getTask_priority()));
            }
        }

        return convertView;
    }
}
