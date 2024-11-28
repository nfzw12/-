package com.example.database_demo1_hou.view.fu.Controller;
import com.example.database_demo1_hou.view.fu.Entity.Emp;
import com.example.database_demo1_hou.view.fu.Service.DepartmentService;
import com.example.database_demo1_hou.view.fu.Entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:7000", allowedHeaders = "*", allowCredentials = "true")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/api/department")
    public List<Department> getAllDepartment() {
        return departmentService.getAllDepartment();
    }

    @PostMapping("/api/adddepartment")
    public String addEmployee(@RequestBody Department dep) {
        departmentService.adddepartment(dep);
        return "Department added successfully!";
    }

    @DeleteMapping("/api/deletedepartment/{userId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int userId) {
        try {
            boolean isDeleted = departmentService.deletedepartment(userId);
            if (isDeleted) {
                return ResponseEntity.ok("部门删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("部门未找到");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
        }
    }

    @PostMapping("/api/updatedepartment")
    public void updateEmployee(@RequestBody Department dep) {
        // 调用服务层的方法更新数据库
        departmentService.updatedepartment(dep);
    }
}
