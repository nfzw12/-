package com.example.database_demo1_hou.view.fu.Service;

import com.example.database_demo1_hou.view.fu.Dao.ApplicantMapper;
import com.example.database_demo1_hou.view.fu.Dao.DepartmentMapper;
import com.example.database_demo1_hou.view.fu.Entity.Applicant;
import com.example.database_demo1_hou.view.fu.Entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantService {
    @Autowired
    private ApplicantMapper applicantMapper;

    public List<Applicant> getAllApplicant() {
        return applicantMapper.findAll();
    }

    public boolean deleteapplicant(int id)
    {
        int result=applicantMapper.deleteapplicant(id);
        return result >0;
    }
}
