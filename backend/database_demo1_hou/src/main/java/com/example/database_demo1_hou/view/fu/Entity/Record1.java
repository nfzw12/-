package com.example.database_demo1_hou.view.fu.Entity;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Record1 {
    private int record_id;
    private int employee_id;
    private LocalDateTime clock_in_time;
    private LocalDateTime clock_out_time;
    private LocalDate attendance_date;
}
