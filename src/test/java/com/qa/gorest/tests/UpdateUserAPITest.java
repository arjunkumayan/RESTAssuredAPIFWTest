package com.qa.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.client.RestClient;
import com.qa.listeners.AllureReportListener;
import com.qa.poio.Users;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


@Epic("EPIC : Create User")
@Feature("US-101 : define the create user feature ")
@Listeners(AllureReportListener.class)
public class UpdateUserAPITest {
	
	String baseURI="https://gorest.co.in";
	String basePath="/public-api/users";
	String token = "g0JIGvfO6SIYnwmMYDM-Kp3nCYVw-xInSDif";
	
	@Test
	@Description("createUserAPI: verify create user api with new user")
	@Severity(SeverityLevel.NORMAL)
	public void createUserAPI() {
		
		// Create a user : using POST Call
		System.out.println("=== Creating a new user =====");
		Users user = new Users("Kailash", "Singh", "male", "13-03-1997", "kail123@gmail.com", "+1-393-200-8245", "https://www.xyz.com", "Premgeet 123", "active");
				
		Response response = RestClient.doPost("JSON", baseURI, basePath, token, true, user);
		JsonPath js = RestClient.getJsonPath(response);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String id = response.jsonPath().getString("result.id");
		System.out.println("new user id is: "+id);
		
		System.out.println("================");
		
		// update the same user: using PUT
		
		user = new Users("Kailash", "Singh", "male", "13-03-1997", "kail123@gmail.com", "+1-393-200-8245", "https://www.xyz.com", "Premgeet 123", "inactive");
		
		Response responseUpdate = RestClient.doPut("JSON", baseURI, basePath+"/"+id, token, true, user);
		
		System.out.println(responseUpdate.prettyPrint());
		
		String updatedId = responseUpdate.jsonPath().getString("result.id");
		
		Assert.assertEquals(updatedId, id);
			
		
	}

	
	@Test
	public void createUserAPIPUTTest_withSetter() {
		
		// Create a user : using POST Call
		System.out.println("=== Creating a new user =====");
		
		Users user = new Users();
		user.setFirst_name("Gullu");
		user.setLast_name("mu");
		user.setGender("female");
		user.setDob("19-05-1980");
		user.setEmail("gullumy@gmail.com");
		user.setPhone("+1-393-200-2225");
		user.setWebsite("https://www.google.com");
		user.setAddress("prem park 1");
		user.setStatus("active");

				
		Response response = RestClient.doPost("JSON", baseURI, basePath, token, true, user);
		JsonPath js = RestClient.getJsonPath(response);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());
		
		String id = response.jsonPath().getString("result.id");
		System.out.println("new user id is: "+id);
		
		System.out.println("================");
		
		// update the same user: using PUT
		
		user.setPhone("+1-393-300-8245");
		user.setStatus("inactive");
		
		Response updateResponse = RestClient.doPut("JSON", baseURI, basePath+"/"+id, token, true, user);
		
		System.out.println(updateResponse.prettyPrint());
		
		String updatedId = updateResponse.jsonPath().getString("result.id");
		
		Assert.assertEquals(updatedId, id);
			
		
	}
}
