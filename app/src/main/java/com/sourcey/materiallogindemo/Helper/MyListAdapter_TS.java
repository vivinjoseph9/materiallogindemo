package com.sourcey.materiallogindemo.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sourcey.materiallogindemo.R;

import java.util.ArrayList;

public class MyListAdapter_TS extends ArrayAdapter<TeamStatus> {

    private LayoutInflater mInflater;
    private ArrayList<TeamStatus> teamstatus;
    private int mViewResourceId;

    public MyListAdapter_TS(Context context, int textViewResourceId, ArrayList<TeamStatus> teamstatus) {
        super(context, textViewResourceId, teamstatus);
        this.teamstatus = teamstatus;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        TeamStatus teamstatus_row = teamstatus.get(position);

        if (teamstatus_row != null) {
            TextView t1 = (TextView) convertView.findViewById(R.id.listview_text1);
            TextView t2 = (TextView) convertView.findViewById(R.id.listview_text2);
            TextView t3 = (TextView) convertView.findViewById(R.id.listview_text3);
            TextView t4 = (TextView) convertView.findViewById(R.id.listview_text4);
            TextView t5 = (TextView) convertView.findViewById(R.id.listview_text5);

            if (t1 != null) {
                t1.setText(teamstatus_row.getTeam_member_name());
            }
            if (t2 != null) {
                t2.setText((teamstatus_row.getTeam_member_status()));
            }
            if (t3 != null) {
                t3.setText((teamstatus_row.getTeam_member_time()));
            }
            if (t4 != null) {
                t4.setText((teamstatus_row.getTeam_member_work_status()));
            }
            if (t5 != null) {
                t5.setText((teamstatus_row.getTeam_member_task()));
            }
        }

        return convertView;
    }
}
