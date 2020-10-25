package com.offcn.service.impl;

import com.offcn.bean.Student;
import com.offcn.mapper.IGetStuInfoMapper;
import com.offcn.service.IGetStuInfoService;
import com.offcn.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class GetStuInfoImpl implements IGetStuInfoService {
    @Override
    public List<Student> getAllStu() {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        List<Student> allStu = mapper.getAllStu();
        session.close();
        return allStu;
    }

    @Override
    public Student getOne(String Stu_id) {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        Student one = mapper.getOne(Stu_id);
        session.close();
        return one;
    }

    @Override
    public Student getOneByName(String Stu_name) {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        Student one = mapper.getOneByName(Stu_name);
        session.close();
        return one;
    }

    @Override
    public int getValidCount() {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        int validCount = mapper.getValidCount();
        return validCount;
    }

    @Override
    public int updateStuInfo(Student student) {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        int res = mapper.updateStuInfo(student);
        session.commit();
        session.close();
        return res;
    }

    @Override
    public int updateAttendence_and_Score(Student student) {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        int res = mapper.updateAttendence_and_Score(student);
        session.commit();
        session.close();
        return res;
    }

    @Override
    public int updateDispline_and_Score(Student student) {
        SqlSession session = MybatisUtils.getSession();
        IGetStuInfoMapper mapper = session.getMapper(IGetStuInfoMapper.class);
        int res = mapper.updateDispline_and_Score(student);
        session.commit();
        session.close();
        return res;
    }
}
