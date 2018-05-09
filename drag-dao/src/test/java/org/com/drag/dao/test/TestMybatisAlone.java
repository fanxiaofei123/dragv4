package org.com.drag.dao.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.com.drag.dao.RoleMapper;
import org.com.drag.model.Role;

/**
 * run mybatis without spring
 * 
 * @author raojun@youedata.com 2017年12月7日
 */
public class TestMybatisAlone {
	public static void main(String[] args) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("mybatis-alone-config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
		Role role = roleMapper.selectByPrimaryKey(1);
		System.out.println(role.getName());
		sqlSession.close();
	}

}
