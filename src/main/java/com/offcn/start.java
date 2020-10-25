package com.offcn;


import com.offcn.bean.Student;
import com.offcn.service.impl.GetStuInfoImpl;
import com.offcn.util.ShowTime;
import com.offcn.view.OmnibusView;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class start {
    public static void main(String[] args) throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        GetStuInfoImpl stu = new GetStuInfoImpl();
        List<Student> allStu = stu.getAllStu();
        new OmnibusView(allStu);
        new ShowTime().start();
    }
}
