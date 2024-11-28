package com.example.database_demo1_hou.view.fu.Controller;

import com.example.database_demo1_hou.view.fu.Entity.Applicant;
import com.example.database_demo1_hou.view.fu.Entity.Department;
import com.example.database_demo1_hou.view.fu.Service.ApplicantService;
import com.example.database_demo1_hou.view.fu.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:7000", allowedHeaders = "*", allowCredentials = "true")
public class ApplicantController {
    @Autowired
    private ApplicantService applicantService;

    @RequestMapping("/api/applicant")
    public List<Applicant> getAllApplicant() {
        return applicantService.getAllApplicant();
    }

    @DeleteMapping("/api/deleteapplicant/{userId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int userId) {
        try {
            boolean isDeleted = applicantService.deleteapplicant(userId);
            if (isDeleted) {
                return ResponseEntity.ok("应聘者删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("应聘者未找到");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
        }
    }
}
