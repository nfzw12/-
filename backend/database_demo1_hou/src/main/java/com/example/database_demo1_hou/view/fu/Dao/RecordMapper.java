package com.example.database_demo1_hou.view.fu.Dao;

import com.example.database_demo1_hou.view.fu.Entity.Emp;
import com.example.database_demo1_hou.view.fu.Entity.Record1;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecordMapper {
    @Select("SELECT * FROM attendance ")
    List<Record1> findAll();

    @Delete("delete from attendance where record_id=#{id}")
    int deleterecord(int id);
}
