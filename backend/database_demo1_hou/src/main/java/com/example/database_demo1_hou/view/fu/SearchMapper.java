/*
package com.example.database_demo1_hou.view.fu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface SearchMapper {
    @Select("CALL GetEmployeesByGender(#{genderFilter})")
    List<Emp> getEmployeesByGender(@Param("genderFilter") String genderFilter);


    @Select("CALL GetSalaryByEmpNo(#{empNo})")
    List<Map<String, Object>> getSalaryByEmpNo(@Param("empNo") int empNo);
}
*/
