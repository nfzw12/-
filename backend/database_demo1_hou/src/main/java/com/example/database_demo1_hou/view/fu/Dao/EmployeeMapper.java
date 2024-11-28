package com.example.database_demo1_hou.view.fu.Dao;

import com.example.database_demo1_hou.view.fu.Entity.Emp;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EmployeeMapper {
    @Select("SELECT * FROM staff ")
    List<Emp> findAll();

    @Select("call clock_in(#{id})")
    String empclock_in(int id);

    @Select("call clock_out(#{id})")
    String empclock_out(int id);

    @Select("SELECT * FROM staff Where id=#{id}")
    List<Emp> searchemp(int id);
    @Insert("INSERT INTO staff(ID, NAME, SEX, AGE, DEPARTMENT, POSITION, PRICE) VALUES (#{id},#{name},#{sex},#{age},#{department},#{position},#{price})")
    void insertEmp(Emp employee);

    @Delete("delete from staff where id=#{id}")
    int deleteEmp(int id);

    @Update("update staff set name=#{name},sex=#{sex},age=#{age},department=#{department},position=#{position},price=#{price} where id=#{id}")
    void updateEmp(Emp employee);
}
