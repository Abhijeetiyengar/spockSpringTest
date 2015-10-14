package com.springSpockTest.test;

import static org.junit.Assert.*;
import spock.lang.Shared;
import spock.lang.Specification;
import spock.lang.Unroll;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springSpockTest.SpringBootApp;
import com.springSpockTest.dao.EmployeeLDAPVerifyService;
import com.springSpockTest.dao.RepositoryTest;
import com.springSpockTest.bean.EmployeeBean;



//@SpringApplicationConfiguration(classes = SpringBootApp.class)
@ContextConfiguration(loader = SpringApplicationContextLoader.class,classes = SpringBootApp.class)
class SpringSpockTest extends Specification {

	@Autowired
	RepositoryTest test;

	@Autowired
	ApplicationContext ctx;


	@Shared isTablesInitialized=false;

	def setup(){


		"initialize Table is they havent" ();
	}



	def "initialize Table is they havent" ()
	{
		if(!isTablesInitialized)
		{
			test.createTables();
			isTablesInitialized=true;
		}
		else
			print "table has laready been initialized";
	}

	def "this is to test if jdbc is not null"()
	{


		when:
		def result1=false;
		result1=test.isJdbcTemplateSet();

		then:
		result1==false;
	}

	def "test for employee id"()
	{
		expect: test.getEmployeeName(2)=="Abh jeet";

	}

	def "test for no employee id exist"()
	{
		when:
		test.getEmployeeName(4);

		then:
		RuntimeException e=thrown();
		e.getMessage()=="No Such employee Id exist"	;
	}

	@Unroll
	def "test data driven Syntaxes for #a and #b" (int emplyeeId,String name)
	{
		when:
		def returnedName="doesnt exist"
		try {
			returnedName=test.getEmployeeName(emplyeeId);
		} catch (Exception e) {
			print "exception occured";

		}
		then:
			returnedName==name;
		where:
			emplyeeId||name
			2||new String("Abh jeet");
			4|| new String("doesnt exist");
	}
	
	
	def "test the employee service with stub"()
	{
		
		setup:
			EmployeeBean bean=new EmployeeBean(2,"abhi","jeet");
			
			EmployeeLDAPVerifyService ldapVerifcatorService=Mock();
			1 * ldapVerifcatorService.verifyEmployee(bean) >> true;
			
			bean=new EmployeeBean(4,"abhi","jeet");
			
			1* ldapVerifcatorService.verifyEmployee(bean) >> false;
			
		when:
			bean=new EmployeeBean(3,"abhi","jeet");
			assert ldapVerifcatorService.verifyEmployee(bean) ==false;
			
			bean=new EmployeeBean(4,"abhi","jeet");
			assert ldapVerifcatorService.verifyEmployee(bean) ==false;
			
			bean=new EmployeeBean(2,"abhi","jeet");
			assert ldapVerifcatorService.verifyEmployee(bean) ==true;
				
		then:
			print 'nothing to do';
		
	}





}
