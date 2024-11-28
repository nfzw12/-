package com.example.database_demo1_hou.view.fu.Service;
import com.example.database_demo1_hou.view.fu.Dao.DepartmentMapper;
import com.example.database_demo1_hou.view.fu.Entity.Department;
import com.example.database_demo1_hou.view.fu.Entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> getAllDepartment() {
        return departmentMapper.findAll();
    }

    public void adddepartment(Department department) {
        departmentMapper.insertdepartment(department);
    }

    public boolean deletedepartment(int id)
    {
        int result=departmentMapper.deletedepartment(id);
        return result >0;
    }

    public void updatedepartment(Department dep)
    {
        departmentMapper.updatedepartment(dep);
    }
}
