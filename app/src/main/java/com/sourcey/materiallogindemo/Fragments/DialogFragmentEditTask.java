package com.sourcey.materiallogindemo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sourcey.materiallogindemo.Helper.DBhelper;
import com.sourcey.materiallogindemo.R;
import com.sourcey.materiallogindemo.TaskAllocationActivity;

public class DialogFragmentEditTask extends AppCompatDialogFragment {

    String str_task_name;
    String str_task_owner;
    String str_task_status;
    String str_task_timeline;
    String str_task_priority;

    EditText t_name;
    EditText t_owner;
    EditText t_status;
    EditText t_timeline;
    Button update;

    Spinner priority;

    String new_task_name;
    String new_task_owner;
    String new_task_status;
    String new_task_timeline;
    String new_task_priority;
    String old_task_name;

    DBhelper myDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            str_task_name = getArguments().getString("frag_task_name","");
            str_task_owner = getArguments().getString("frag_task_owner","");
            str_task_status = getArguments().getString("frag_task_status","");
            str_task_timeline = getArguments().getString("frag_task_timeline","");
            str_task_priority = getArguments().getString("frag_task_priority","");
        } else {
            Toast.makeText(getActivity(), "Task details could not be retrieved !!", Toast.LENGTH_SHORT).show();        }

        View view = inflater.inflate(R.layout.fragment_edit_task, container, false);

        t_name = (EditText) view.findViewById(R.id.input_task);
        t_owner = (EditText) view.findViewById(R.id.input_task_owner);
        t_status = (EditText) view.findViewById(R.id.input_task_status);
        t_timeline = (EditText) view.findViewById(R.id.input_timeline);
        priority =  (Spinner) view.findViewById(R.id.input_priority);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.priority_list));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        priority.setAdapter(myAdapter);

        t_name.setText(str_task_name);
        t_owner.setText(str_task_owner);
        t_status.setText(str_task_status);
        t_timeline.setText(str_task_timeline);

        old_task_name = str_task_name;

        update = (Button) view.findViewById(R.id.btn_update_task) ;

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTaskValues();
            }
        });

        return view;
    }

    private void updateTaskValues() {
        new_task_name = t_name.getText().toString() ;
        new_task_owner = t_owner.getText().toString() ;
        new_task_status = t_status.getText().toString() ;
        new_task_timeline = t_timeline.getText().toString() ;
        new_task_priority = priority.getSelectedItem().toString() ;

        myDB = new DBhelper(getActivity());
        boolean updateTask = myDB.updateTask_TA(old_task_name, new_task_name, new_task_owner, new_task_status, new_task_timeline, new_task_priority);

        if (updateTask == true){
            Toast.makeText(getActivity(), "Task details updated !!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), TaskAllocationActivity.class));
        } else {
            Toast.makeText(getActivity(), "Task details could not be updated !!", Toast.LENGTH_SHORT).show();
        }
    }
}
