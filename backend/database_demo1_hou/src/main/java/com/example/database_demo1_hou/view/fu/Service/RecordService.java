package com.example.database_demo1_hou.view.fu.Service;

import com.example.database_demo1_hou.view.fu.Dao.EmployeeMapper;
import com.example.database_demo1_hou.view.fu.Dao.RecordMapper;
import com.example.database_demo1_hou.view.fu.Entity.Emp;
import com.example.database_demo1_hou.view.fu.Entity.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordService {
    @Autowired
    private RecordMapper recordMapper;

    public List<Record1> getAllRecord() {
        return recordMapper.findAll();
    }

    public boolean deleterecord(int id)
    {
        int result=recordMapper.deleterecord(id);
        return result >0;
    }
}
