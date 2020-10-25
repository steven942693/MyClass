package com.offcn.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

public class MybatisUtils {

    private static SqlSessionFactory factory = null;

    static{
        String resource = "mybatis.xml";
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        factory = new SqlSessionFactoryBuilder().build(in);
    }

    //获取数据库的连接
    public static SqlSession getSession(){
        return factory.openSession();
    }
}
