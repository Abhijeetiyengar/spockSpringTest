package com.springSpockTest.dao;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springSpockTest.bean.EmployeeBean;

@Repository
public class RepositoryTest {
	
	private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RepositoryTest(JdbcTemplate jdbcTemplate) {
    	System.out.println("HI I am in the constructor");
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public void createTables()
    {
    	jdbcTemplate.execute("Create Table employee (empid integer,firstname VARCHAR(50),lastname VARCHAR(50))");
    	
    	jdbcTemplate.execute("insert into employee values(2,'Abh','jeet')");
    }

    public boolean isJdbcTemplateSet()
    {
    	
    	return jdbcTemplate==null;
    }
    
    public String getEmployeeName(Integer emplInteger)
    {
    	List<Map<String, Object>> resultList=jdbcTemplate.queryForList("select firstName,lastName from employee where empid="+emplInteger);
    
    	if(!resultList.isEmpty())
    	{
    		return resultList.get(0).get("firstName").toString()+" "+resultList.get(0).get("lastName").toString();
    	}
    	else
    		throw new RuntimeException("No Such employee Id exist");
    
    }
    
   

}
