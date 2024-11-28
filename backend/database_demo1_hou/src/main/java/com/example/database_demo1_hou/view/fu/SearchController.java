/*
package com.example.database_demo1_hou.view.fu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:7000", allowedHeaders = "*", allowCredentials = "true")
public class SearchController {
    @Autowired
    private SearchService employeeService;
    @GetMapping("api/genderselect")
    public List<Emp> getEmployeesByGender(@RequestParam String gender) {
        return employeeService.getEmployeesByGender(gender);
    }

    @GetMapping("/api/salary")
    public List<Map<String, Object>> getSalaryByEmpNo(@RequestParam int empNo) {
        return employeeService.getSalaryByEmpNo(empNo);
    }
}
*/
