package com.fh.util;

import org.activiti.engine.ProcessEngineConfiguration;

public class Createactiviti {

	public void createactivityTable(){
	     
	    ProcessEngineConfiguration p = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
	    p.setJdbcDriver("com.mysql.jdbc.Driver");
	    p.setJdbcUrl("jdbc:mysql://123.57.67.197:3306/ccweb?useUnicode=true&characterEncoding=utf-8");
	    p.setJdbcUsername("robot");
	    p.setJdbcPassword("robotrobot");
	     
	    p.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
	    p.buildProcessEngine();
	}
	
	public static void main(String[] args) {
		Createactiviti createactiviti=new Createactiviti();
		createactiviti.createactivityTable();
	}
	
}
