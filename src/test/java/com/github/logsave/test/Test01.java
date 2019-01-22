package com.github.logsave.test;

import java.io.IOException;
import java.io.Reader;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.github.logsave.pojo.Emp;

public class Test01 {
	
	public static void creat(SqlSession sqlSession) {
		Emp emp = new Emp();
		/**
		 *  测试时运行第二次会报错：Duplicate entry '7499' for key 'PRIMARY'
		 *  原因是主键冲突了，修改Empno即可。
		 */
		emp.setEmpno(7499); 
		emp.setEname("ALLEN");
		emp.setJob("SALESMAN");
		emp.setMgr(7698);
		emp.setHiredate(new Date());
		emp.setSal(1600.00);
		
		System.out.println("数据更新行数：" 
				+ sqlSession.insert("com.github.logsave.pojo.EmpMapper.creatOne",emp));
		sqlSession.commit(); 	// 事务提交
	}
	
	public static void selectByEmpNo(SqlSession sqlSession,int empno) {
		System.out.println(sqlSession.selectOne("com.github.logsave.pojo.EmpMapper.selOne",empno));
	}
	
	public static void main(String[] args) {
		
		try {
			// 1、取得操作的SqlSession对象
			String resource = "mybatis-config.xml"; // MyBatis配置文件路径
			Reader reader = Resources.getResourceAsReader(resource); // 当前配置文件输入流
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); // 数据库会话工厂
			SqlSession sqlSession = sqlSessionFactory.openSession(); // 取得连接
			
			// 2、操作简单Java类	
			creat(sqlSession); 					// 增加数据
			selectByEmpNo(sqlSession,7499); 	// 按empno查询数据
	
			sqlSession.close();		// 关闭连接
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
