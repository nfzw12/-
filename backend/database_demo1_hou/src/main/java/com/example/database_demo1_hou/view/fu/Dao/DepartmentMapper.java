package com.example.database_demo1_hou.view.fu.Dao;
import com.example.database_demo1_hou.view.fu.Entity.Department;

import com.example.database_demo1_hou.view.fu.Entity.Emp;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface DepartmentMapper {
    @Select("SELECT * FROM department ")
    List<Department> findAll();

    @Insert("INSERT INTO department(id, name, amount, director_name) VALUES(#{id},#{name},#{amount},#{director_name}) ")
    void insertdepartment(Department department);

    @Delete("delete from department where id=#{id}")
    int deletedepartment(int id);

    @Update("update department set name=#{name},amount=#{amount},director_name=#{director_name} where id=#{id}")
    void updatedepartment(Department department);
}
