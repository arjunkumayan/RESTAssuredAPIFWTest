package com.qa.gorest.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.client.RestClient;
import com.qa.constants.Constants;
import com.qa.listeners.AllureReportListener;
import com.qa.poio.Users;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
@Listeners(AllureReportListener.class)
public class DeleteUserAPITest {
	
	String baseURI="https://gorest.co.in";
	String basePath="/public-api/users";
	String token = "g0JIGvfO6SIYnwmMYDM-Kp3nCYVw-xInSDif";
	
	@Test
	@Description("deleteUserAPI: verify delete user api with new user")
	@Severity(SeverityLevel.CRITICAL)
	public void deleteUserTest() {

		// Create a user : using POST Call
		System.out.println("=== Creating a new user =====");

		Users user = new Users();
		user.setFirst_name("Gullu1");
		user.setLast_name("mukesh");
		user.setGender("male");
		user.setDob("19-08-1980");
		user.setEmail("gullddumy@gmail.com");
		user.setPhone("+1-11-200-2225");
		user.setWebsite("https://www.google.com");
		user.setAddress("prem park 123");
		user.setStatus("active");

		Response response = RestClient.doPost("JSON", baseURI, basePath, token, true, user);
		JsonPath js = RestClient.getJsonPath(response);
		System.out.println(response.getStatusCode());
		System.out.println(response.prettyPrint());

		String id = response.jsonPath().getString("result.id");
		System.out.println("new user id is: " + id);

		System.out.println("================");

		// delete the same user: using delete

		Response deleteResponse = RestClient.doDelete("JSON", baseURI, basePath + "/" + id, token, true);

		System.out.println(deleteResponse.prettyPrint());

		String updatedId = deleteResponse.jsonPath().getString("result");
		Assert.assertNull(deleteResponse.jsonPath().getString("result"));

		int responseCode = deleteResponse.jsonPath().get("_meta.code");
		Assert.assertEquals(responseCode, Constants.HTTP_STATUS_CODE_204);

		int limit = deleteResponse.jsonPath().get("_meta.rateLimit.limit");
		int remaining = deleteResponse.jsonPath().get("_meta.rateLimit.remaining");
		int reset = deleteResponse.jsonPath().get("_meta.rateLimit.reset");

		Assert.assertEquals(remaining, limit-reset);
	}

}
