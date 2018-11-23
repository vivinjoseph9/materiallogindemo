package com.sourcey.materiallogindemo.Helper;

public class User {

    private String task_name;
    private String task_owner;
    private String task_status;
    private String task_timeline;
    private String task_priority;

    public User (String t_name, String t_owner, String t_status, String t_timeline, String t_priority) {
        task_name = t_name;
        task_owner = t_owner;
        task_status = t_status;
        task_timeline = t_timeline;
        task_priority = t_priority;

    }

    public String getTask_name() {
        return task_name;
    }

    public String getTask_owner() {
        return task_owner;
    }

    public String getTask_status() {
        return task_status;
    }

    public String getTask_timeline() {
        return task_timeline;
    }

    public String getTask_priority() {
        return task_priority;
    }

}
