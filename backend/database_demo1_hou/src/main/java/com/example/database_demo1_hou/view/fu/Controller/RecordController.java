package com.example.database_demo1_hou.view.fu.Controller;

import com.example.database_demo1_hou.view.fu.Entity.Emp;
import com.example.database_demo1_hou.view.fu.Entity.Record1;
import com.example.database_demo1_hou.view.fu.Service.EmpService;
import com.example.database_demo1_hou.view.fu.Service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:7000", allowedHeaders = "*", allowCredentials = "true")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @RequestMapping("/api/record")
    public List<Record1> getAllEmployees() {
        return recordService.getAllRecord();
    }



    @DeleteMapping("/api/deleterecord/{userId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int userId) {
        try {
            boolean isDeleted = recordService.deleterecord(userId);
            if (isDeleted) {
                return ResponseEntity.ok("记录删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("员工未找到");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
        }
    }
}
