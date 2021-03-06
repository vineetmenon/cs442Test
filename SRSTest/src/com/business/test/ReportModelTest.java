package com.business.test;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.business.ReportModel;
import com.database.DatabaseConnection;

public class ReportModelTest {
	
	static ReportModel testModel;
	@BeforeClass
	public static void setup() {
		testModel = new ReportModel();
	}
	
	@Test
	public void testsetDBConn() {
		assertNotNull("check connection",testModel.dbconnect);
	}
	
	@Test
	public void testgetSchoolList() {
		testModel.getNewData();
		Map<String, String> testMap = testModel.getSchoolList();
		assertFalse("check if school list returns",testMap.isEmpty());
	}
	
	@Test
	public void testgetParameterList() {
		testModel.getNewData();
		Map<String, String> testMap = testModel.getParameterList();
		assertFalse("check if parameter list returns",testMap.isEmpty());
	}

	@Test
	public void testgetNewData() {
		testModel.getNewData();
		
		String QueryString = "SELECT count(id) from School";
		ResultSet rs = null;
		try {
			Statement statement = DatabaseConnection.getConnection()
					.createStatement();
			rs = statement.executeQuery(QueryString);

			rs.next();
			boolean check = (rs.getInt(1) > 0);
			assertTrue("school count is greater than zero",check );
		} catch (Exception e) {
			fail("exception occured");
		}
	}
	
	@Test
	public void testclearData() {
		testModel.clearExistingData();
		
		String QueryString = "SELECT count(id) from School";
		ResultSet rs = null;
		try {
			Statement statement = DatabaseConnection.getConnection()
					.createStatement();
			rs = statement.executeQuery(QueryString);

			rs.next();
			boolean check = (rs.getInt(1) == 0);
			assertTrue("school count is greater than zero",check );
		} catch (Exception e) {
			fail("exception occured");
		}
	}
	
	@AfterClass
	public static void tearDown() {
		testModel.clearExistingData();
		testModel.getNewData();
	}
	
}
