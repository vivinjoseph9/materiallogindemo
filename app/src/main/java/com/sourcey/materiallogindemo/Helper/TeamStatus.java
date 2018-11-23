package com.sourcey.materiallogindemo.Helper;

public class TeamStatus {

        private String team_member_name;
        private String team_member_status;
        private String team_member_time;
        private String team_member_work_status;
        private String team_member_task;

        public TeamStatus (String t_member_name, String t_member_status, String t_member_time, String t_member_work_status, String t_member_task) {
            team_member_name = t_member_name;
            team_member_status = t_member_status;
            team_member_time = t_member_time;
            team_member_work_status = t_member_work_status;
            team_member_task = t_member_task;

        }

        public String getTeam_member_name() {
            return team_member_name;
        }

        public String getTeam_member_status() {
            return team_member_status;
        }

        public String getTeam_member_time() {
            return team_member_time;
        }

        public String getTeam_member_work_status() {
            return team_member_work_status;
        }

        public String getTeam_member_task() {
            return team_member_task;
        }

    }


