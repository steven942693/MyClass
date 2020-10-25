package com.offcn.service;

import com.offcn.bean.Student;

import java.util.List;

public interface IGetStuInfoService {
    public List<Student> getAllStu();
    public Student getOne(String Stu_id);
    public Student getOneByName(String Stu_name);
    public int getValidCount();
    public int updateStuInfo(Student student);
    public int updateAttendence_and_Score(Student student);
    public int updateDispline_and_Score(Student student);
}
