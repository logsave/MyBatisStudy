package com.github.logsave.test;

import java.io.IOException;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.github.logsave.pojo.Emp;

public class Test02 {
	// 数据增加
	public static void creat(SqlSession sqlSession) {
		Emp emp = new Emp();
		emp.setEmpno(7369); 
		emp.setEname("SMITH");
		emp.setJob("SALESMAN");
		emp.setMgr(7698);
		
		Calendar c = Calendar.getInstance();
		c.set(1983,9,20);
		Date date = c.getTime();
		emp.setHiredate(date);
		
		emp.setSal(1250.00);
		
		System.out.println("【INSERT】数据更新行数：" 
				+ sqlSession.insert("com.github.logsave.pojo.EmpMapper.creatOne",emp));
		sqlSession.commit(); 	// 事务提交
	}
	
	// 数据更新
	public static void update(SqlSession sqlSession) {
		Emp emp = new Emp();
		emp.setEmpno(7369); 
		emp.setEname("Logsave");
		emp.setJob("MANAGER");
//		emp.setMgr(7839);   // 注释掉数据库中对应值为null
		
		Calendar c = Calendar.getInstance();
		c.set(2015,10,20);
		Date date = c.getTime();
		emp.setHiredate(date);
		emp.setSal(1600.00);
		
		System.out.println("【UPDATE】数据更新行数：" 
				+ sqlSession.update("com.github.logsave.pojo.EmpMapper.updateOne",emp));
		sqlSession.commit(); 	// 事务提交
	}
	// 数据删除
	public static void remove(SqlSession sqlSession) {
		int empno = 7499; 	// 删除数据的empno
		System.out.println("【DELETE】数据更新行数：" + 
				sqlSession.delete("com.github.logsave.pojo.EmpMapper.removeOne",empno));
		sqlSession.commit();
	}
	// 查询单条数据
	public static void selectByEmpNo(SqlSession sqlSession) {
		int empno = 7369;
		System.out.println("【SELECT】selectByEmpno：" + empno + "\n \t" +  
				sqlSession.selectOne("com.github.logsave.pojo.EmpMapper.selOne",empno));
	}
	
	// 查询全部数据
	public static void selectAll(SqlSession sqlSession) {
		List<Emp> empList = sqlSession.selectList("com.github.logsave.pojo.EmpMapper.selAll");
		System.out.println("【SELECT】selectAll：");
		for(Emp emp: empList) {
			System.out.println("\t" + emp);
		}	
	}
	// 分页操作
	public static void split(SqlSession sqlSession) {
		// 分页操作必要的参数
		int currentPage = 1; // 当前所在页
		int lineSize = 3; // 每页显示的长度
		// 需要将参数设置为Map集合后传递到调用的方法里
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", "ename");
		map.put("keyWord", "%o%");
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		List<Emp> empList = sqlSession.selectList("com.github.logsave.pojo.EmpMapper.selAllBySplit", map);
		System.out.println("【SELECT】分页操作：");
		for(Emp emp: empList) {
			System.out.println("\t" + emp);
		}	
	}
	
	public static void count(SqlSession sqlSession) {
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("column", "ename");
		map.put("keyWord", "%o%");
		System.out.println("【COUNT】数据个数：" 
				+ sqlSession.selectOne("com.github.logsave.pojo.EmpMapper.count", map) );
	}
	
	public static void main(String[] args) {
		
		try {
			// 1、取得操作的SqlSession对象
			String resource = "mybatis-config.xml"; // MyBatis配置文件路径
			Reader reader = Resources.getResourceAsReader(resource); // 当前配置文件输入流
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader); // 数据库会话工厂
			SqlSession sqlSession = sqlSessionFactory.openSession(); // 取得连接
			
			// 2、操作简单Java类	
			// 为了测试方便，将各个方法分别编写函数进行测试。在函数中改变方法内容进行测试。
//			creat(sqlSession); 					// 增加数据
//			selectByEmpNo(sqlSession); 			// 按empno查询数据			
//			update(sqlSession);					// 更新数据
//			remove(sqlSession);					// 删除数据
			selectAll(sqlSession);				// 查询全部数据
//			count(sqlSession); 					// 数据个数
//			split(sqlSession);					// 分页查询
			sqlSession.close();		// 关闭连接
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
