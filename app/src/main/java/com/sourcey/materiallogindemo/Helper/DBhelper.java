package com.sourcey.materiallogindemo.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    /*
    Columns TASK ALLOC TABLE
     */
    static String TASK_NAME = "TASK_NAME";
    static String TASK_OWNER = "TASK_OWNER";
    static String TASK_STATUS = "TASK_STATUS";
    static String TASK_TIMELINE = "TASK_TIMELINE";
    static String TASK_PRIORITY = "TASK_PRIORITY";

    /*
    Columns TEAM STATUS TABLE
    */
    static String TEAM_MEMBER_NAME = "TEAM_MEMBER_NAME";
    static String TEAM_MEMBER_STATUS = "TEAM_MEMBER_STATUS";
    static String TEAM_MEMBER_STATUS_TIME = "TEAM_MEMBER_STATUS_TIME";
    static String TEAM_MEMBER_WORK_STATUS = "TEAM_MEMBER_WORK_STATUS";
    static String TEAM_MEMBER_TASK = "TEAM_MEMBER_TASK";

    /*
    DB Properties
     */
    static String DB_NAME = "bcmdb.db";
    static String TB_NAME = "taskalloc_table";
    static String TB_NAME_TS = "teamstatus_table";

    public DBhelper(Context context) {
        super(context, DB_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_TABLE = "CREATE TABLE " +  TB_NAME + " (TASK_NAME TEXT PRIMARY KEY, " +
                                                           " TASK_OWNER TEXT, " +
                                                           " TASK_STATUS TEXT, " +
                                                           " TASK_TIMELINE TEXT, " +
                                                           " TASK_PRIORITY TEXT) " ;
        
        String CREATE_TABLE_TS = "CREATE TABLE " +  TB_NAME_TS + " (TEAM_MEMBER_NAME TEXT PRIMARY KEY, " +
                                                                 " TEAM_MEMBER_STATUS TEXT, " +
                                                                 " TEAM_MEMBER_STATUS_TIME TEXT, " +
                                                                 " TEAM_MEMBER_WORK_STATUS TEXT, " +
                                                                 " TEAM_MEMBER_TASK TEXT) " ;

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_TS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF  EXISTS " + TB_NAME);
        db.execSQL(" DROP TABLE IF  EXISTS " + TB_NAME_TS);

        onCreate(db);
    }

    /*
    Insert rows into the Task Allocation Table
     */
    public boolean addData_to_TA(String task_name, String task_owner, String task_status, String task_timeline, String task_priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put (TASK_NAME, task_name);
        contentValues.put (TASK_OWNER, task_owner);
        contentValues.put (TASK_STATUS, task_status);
        contentValues.put (TASK_TIMELINE, task_timeline);
        contentValues.put (TASK_PRIORITY, task_priority);

        long result = db.insert(TB_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    Select all rows from the Task Allocation Table
    */
    public Cursor getListContents_TA() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery (" SELECT * FROM " + TB_NAME, null);
        return data;
    }

    /*
    Select rows from the Task Allocation Table where name=name and status="in progress"
    */
    public Cursor getSelectedListContents_TA(String task_owner) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery (" SELECT * FROM " + TB_NAME + " WHERE TASK_OWNER = '" + task_owner + "' AND TASK_STATUS='In Progress'", null);
        return data;
    }
    
    /*
    Update row in the Task Allocation Table to mark the task as complete
    */
    public boolean updateTaskAsComplete_TA(String task_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_STATUS,"DONE");
     //   String condition = "TASK_NAME=" + task_name;
    //this works  String strSQL = "UPDATE " + TB_NAME + " SET TASK_STATUS = 'DONE' WHERE TASK_NAME = "+ "'" +task_name + "';";
        long result = db.update(TB_NAME, contentValues, "TASK_NAME = " + "'" + task_name + "'", null);
    //this works  db.execSQL(strSQL);

    //   long result = 1;
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    Update a row in the Task Allocation Table
    */
    public boolean updateTask_TA(String old_task_name, String new_task_name, String task_owner, String task_status, String task_timeline, String task_priority){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put (TASK_NAME, new_task_name);
        contentValues.put (TASK_OWNER, task_owner);
        contentValues.put (TASK_STATUS, task_status);
        contentValues.put (TASK_TIMELINE, task_timeline);
        contentValues.put (TASK_PRIORITY, task_priority);

        //   String condition = "TASK_NAME=" + task_name;
        //this works  String strSQL = "UPDATE " + TB_NAME + " SET TASK_STATUS = 'DONE' WHERE TASK_NAME = "+ "'" +task_name + "';";
        long result = db.update(TB_NAME, contentValues, "TASK_NAME = " + "'" + old_task_name + "'", null);
        //this works  db.execSQL(strSQL);

        //   long result = 1;
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    Delete rows in the Task Allocation Table
    */
    public boolean deleteTask_TA(String task_name) {
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TB_NAME,"TASK_NAME = " + "'" + task_name + "'", null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    Insert rows into the Team Status Table
    */
    public boolean addData_to_TS(String team_member_name, String team_member_status, String team_member_status_time, String team_member_work_status, String team_member_task){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put (TEAM_MEMBER_NAME, team_member_name);
        contentValues.put (TEAM_MEMBER_STATUS, team_member_status);
        contentValues.put (TEAM_MEMBER_STATUS_TIME, team_member_status_time);
        contentValues.put (TEAM_MEMBER_WORK_STATUS, team_member_work_status);
        contentValues.put (TEAM_MEMBER_TASK, team_member_task);

        long result = db.insert(TB_NAME_TS, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /*
    Select all rows from the Team Status Table
    */
    public Cursor getListContents_TS() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery (" SELECT * FROM " + TB_NAME_TS, null);
        return data;
    }

    /*
    Select rows from the Task Allocation Table where name=name and status="in progress"
    */
    public Cursor getAllNames_TS() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery (" SELECT " + TEAM_MEMBER_NAME + " FROM " + TB_NAME_TS, null);
        return data;
    }

    /*
    Update row in the Task Allocation Table to mark the task as complete
    */
    public boolean updateTaskStatus_TS(String team_member_name, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TEAM_MEMBER_WORK_STATUS, value);

        long result = db.update(TB_NAME_TS, contentValues, "TEAM_MEMBER_NAME = " + "'" + team_member_name + "'", null);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

}

