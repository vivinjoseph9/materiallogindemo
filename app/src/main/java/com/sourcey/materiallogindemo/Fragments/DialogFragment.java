package com.sourcey.materiallogindemo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Helper.DBhelper;
import com.sourcey.materiallogindemo.R;
import com.sourcey.materiallogindemo.TaskAllocationActivity;

public class DialogFragment extends AppCompatDialogFragment {
    private TextView textView;
    String str_task_name;
    String str_task_owner;
    String str_task_status;
    String str_task_timeline;
    String str_task_priority;

    Button task_complete;
    Button task_edit_details;
    Button task_delete;

    DBhelper myDB;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
     //  String strtext = getArguments().getString("frag_task_name");
        if (getArguments() != null) {
            str_task_name = getArguments().getString("frag_task_name","");
            str_task_owner = getArguments().getString("frag_task_owner","");
            str_task_status = getArguments().getString("frag_task_status","");
            str_task_timeline = getArguments().getString("frag_task_timeline","");
            str_task_priority = getArguments().getString("frag_task_priority","");
        } else {
            str_task_name = "no task found";
        }

        View view = inflater.inflate(R.layout.fragment_modifyitem, container, false);
        textView = (TextView) view.findViewById(R.id.textView8);
        textView.setText("Selected Task : " + str_task_name);

        task_complete = (Button) view.findViewById(R.id.btn_mark_complete) ;
        task_delete = (Button) view.findViewById(R.id.btn_task_delete) ;
        task_edit_details = (Button) view.findViewById(R.id.btn_edit_task) ;

        task_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                markTaskAsComplete();
            }
        });

        task_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTask();

            }
        });

        task_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });

        return view;
    }

    private void editTask() {
        Bundle bundle = new Bundle();
        bundle.putString("frag_task_name", str_task_name);
        bundle.putString("frag_task_owner", str_task_owner);
        bundle.putString("frag_task_status", str_task_status);
        bundle.putString("frag_task_timeline", str_task_timeline);
        bundle.putString("frag_task_priority", str_task_priority);

        FragmentManager fragmentManager = getFragmentManager();

        DialogFragmentEditTask fragment = new DialogFragmentEditTask();

        fragment.setArguments(bundle);

        fragment.show(fragmentManager, "fragment2");
    }

    private void deleteTask() {
        myDB = new DBhelper(getActivity());
        boolean deleteTask = myDB.deleteTask_TA(str_task_name);

        if (deleteTask == true){
            Toast.makeText(getActivity(), "Task deleted !!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), TaskAllocationActivity.class));
        } else {
            Toast.makeText(getActivity(), "Task could not be deleted !!", Toast.LENGTH_SHORT).show();
        }
    }

    private void markTaskAsComplete() {
        myDB = new DBhelper(getActivity());
        boolean updateTask = myDB.updateTaskAsComplete_TA(str_task_name);

        if (updateTask == true){
            Toast.makeText(getActivity(), "Task marked as completed !!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), TaskAllocationActivity.class));
        } else {
            Toast.makeText(getActivity(), "Task could not be updated !!", Toast.LENGTH_SHORT).show();
        }
    }
}
