package com.qa.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.client.RestClient;
import com.qa.listeners.AllureReportListener;
import com.qa.poio.Users;
import com.qa.util.ExcelUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
@Listeners(AllureReportListener.class)
public class CreateUserAPITest {
	

	String baseURI="https://gorest.co.in";
	String basePath="/public-api/users";
	String token = "g0JIGvfO6SIYnwmMYDM-Kp3nCYVw-xInSDif";
	
	@DataProvider
	public Object[][] getUserData() {
		Object userData[][] = ExcelUtil.getTestData("createuser");
		return userData;
	}
	
	@Test(dataProvider = "getUserData")
	@Description("createUserPOSTAPI: verify create user api with new user")
	@Severity(SeverityLevel.CRITICAL)
	public void createUserAPI(String first_name,String last_name,String gender,String dob,String email,String phone,String website,	String address,String status) {
		Users user = new Users(first_name, last_name, gender, dob, email, phone, website, address, status);
		Response response = RestClient.doPost("JSON", baseURI, basePath, token, true, user);
		JsonPath js = RestClient.getJsonPath(response);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
//		Assert.assertEquals(js.getString("result.first_name"), user.getFirst_name());
//		Assert.assertEquals(js.getString("result.last_name"), user.getLast_name());
//		Assert.assertEquals(js.getString("result.gender"), user.getGender());
//		Assert.assertEquals(js.getString("result.dob"), user.getDob());
//		Assert.assertEquals(js.getString("result.email"), user.getEmail());
//		Assert.assertEquals(js.getString("result.phone"), user.getPhone());
//		Assert.assertEquals(js.getString("result.website"), user.getWebsite());
//		Assert.assertEquals(js.getString("result.address"), user.getAddress());
		
	}
	
	
	@Test
	public void createUserHardCodedAPITest() {
		
		Users user = new Users("Pratik123", "Shitole11", "female", "1998-01-01", "mahmqq1pddnge@gmail.com", "+1-393-200-8273", "https://www.google.com", "premgeet test 01/234", "active");
		Response response = RestClient.doPost("JSON", baseURI, basePath, token, true, user);
		JsonPath js = RestClient.getJsonPath(response);
		System.out.println(response.prettyPrint());
		
		Assert.assertEquals(js.getString("result.first_name"), user.getFirst_name());
		Assert.assertEquals(js.getString("result.last_name"), user.getLast_name());
		Assert.assertEquals(js.getString("result.gender"), user.getGender());
		Assert.assertEquals(js.getString("result.dob"), user.getDob());
		Assert.assertEquals(js.getString("result.email"), user.getEmail());
		Assert.assertEquals(js.getString("result.phone"), user.getPhone());
		Assert.assertEquals(js.getString("result.website"), user.getWebsite());
		Assert.assertEquals(js.getString("result.address"), user.getAddress());
		
	}
	
	
	
	
	

}
