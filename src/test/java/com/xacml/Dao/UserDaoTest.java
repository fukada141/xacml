package com.xacml.Dao;

import com.xacml.Entity.*;
import com.xacml.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest {

    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
//        String a = userDao.getSetDesBySetID("pls-0001");
//        String b = userDao.getAlgBySetID("pls-0001");
//        List<String> a = userDao.queryPolIDBySetID("pls-0001");
//        Policy a = userDao.queryPolByID("pol-0001");
//        Environment environment = userDao.queryEnvByID_g("urn:oasis:names:tc:xacml:1.0:function:string-unequal");
//        System.out.println(environment.toString());
//        System.out.println(a);
        String PolicyID = "rul-0001";
        String FuncId = "urn:oasis:names:tc:xacml:1.0:function:and";
        List<String> msg = userDao.queryFuncIDByRulID(PolicyID);
        Conditions conditions = userDao.queryConByID(FuncId);
        System.out.println(msg);
        System.out.println(conditions.toString());
        sqlSession.commit();
        sqlSession.close();
    }
}
