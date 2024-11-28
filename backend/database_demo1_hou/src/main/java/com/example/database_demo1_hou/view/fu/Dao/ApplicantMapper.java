package com.example.database_demo1_hou.view.fu.Dao;

import com.example.database_demo1_hou.view.fu.Entity.Applicant;
import com.example.database_demo1_hou.view.fu.Entity.Department;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplicantMapper {
    @Select("SELECT * FROM employment ")
    List<Applicant> findAll();

    @Delete("delete from employment where id=#{id}")
    int deleteapplicant(int id);
}
