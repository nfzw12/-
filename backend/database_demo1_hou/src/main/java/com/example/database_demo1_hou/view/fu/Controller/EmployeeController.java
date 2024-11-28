package com.example.database_demo1_hou.view.fu.Controller;

import com.example.database_demo1_hou.view.fu.Service.EmpService;
import com.example.database_demo1_hou.view.fu.Entity.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:7000", allowedHeaders = "*", allowCredentials = "true")
public class EmployeeController {

    @Autowired
    private EmpService employeeService;

    @RequestMapping("/api/employees")
    public List<Emp> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("/api/addemployees")
    public String addEmployee(@RequestBody Emp employee) {
        employeeService.addEmployee(employee);
        return "Employee added successfully!";
    }
    @PostMapping("/api/empclock_in/{id}")
    public String clock_in(@PathVariable int id) {
        employeeService.Empclock_in(id);
        return "clock in successfully!";
    }

    @PostMapping("/api/empclock_out/{id}")
    public String clock_out(@PathVariable int id) {
        employeeService.Empclock_out(id);
        return "clock out successfully!";
    }


    @PostMapping("/api/searchemployees/{userId}")
    public List<Emp> searchEmployees(@PathVariable int userId) {
        return employeeService.searchemployee(userId);
    }

    @DeleteMapping("/api/deleteemployees/{userId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int userId) {
        try {
            boolean isDeleted = employeeService.deleteEmployee(userId);
            if (isDeleted) {
                return ResponseEntity.ok("员工删除成功");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("员工未找到");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("删除失败");
        }
    }

    @PostMapping("/api/updateemployees")
    public void updateEmployee(@RequestBody Emp employee) {
        // 调用服务层的方法更新数据库
         employeeService.updateEmployee(employee);
    }
}