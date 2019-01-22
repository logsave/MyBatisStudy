-- 创建'mybatis'数据库
CREATE DATABASE mybatis CHARACTER SET UTF8;
-- 创建'emp'数据表
CREATE TABLE emp (
		empno 		int(4),
		ename 		varchar(10) NOT NULL ,
		job  		varchar(9) ,
		mgr  		int(4) ,
		hiredate  	date ,
		sal  		double(7,2) ,
		PRIMARY KEY (empno)
);