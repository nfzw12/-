package com.example.database_demo1_hou.view.fu.Service;

import com.example.database_demo1_hou.view.fu.Dao.EmployeeMapper;
import com.example.database_demo1_hou.view.fu.Entity.Emp;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Emp> getAllEmployees() {
        return employeeMapper.findAll();
    }

    public List<Emp> searchemployee(int id) {
        return employeeMapper.searchemp(id);
    }

    public void addEmployee(Emp employee) {
        employeeMapper.insertEmp(employee);
    }

    public String Empclock_in(int id){return employeeMapper.empclock_in(id);}

    public String Empclock_out(int id){return employeeMapper.empclock_out(id);}

    public boolean deleteEmployee(int id)
    {
        int result=employeeMapper.deleteEmp(id);
        return result >0;
    }

    public void updateEmployee(Emp employee)
    {
        employeeMapper.updateEmp(employee);
    }

}