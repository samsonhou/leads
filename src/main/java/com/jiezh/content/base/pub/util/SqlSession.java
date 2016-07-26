package com.jiezh.content.base.pub.util;

import org.mybatis.spring.SqlSessionTemplate;

public class SqlSession {
	
	private SqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	public SqlSessionTemplate getSqlTemplate(){
		return sqlSessionTemplate;
	}
}
