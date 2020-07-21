package com.qa.gorest.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.client.RestClient;
import com.qa.constants.Constants;
import com.qa.listeners.AllureReportListener;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
@Listeners(AllureReportListener.class)
public class GetUserAPITest {
	
	String baseURI="https://gorest.co.in";
	String basePath="/public-api/users";
	String token = "g0JIGvfO6SIYnwmMYDM-Kp3nCYVw-xInSDif";
	
	@Test
	@Description("getUserListAPITest: verify get user api with existing user")
	@Severity(SeverityLevel.NORMAL)
	public void getUserListAPITest() {
		Response response = RestClient.doGet("JSON", baseURI, basePath, token, true);
		
		Assert.assertEquals(RestClient.getStatusCode(response), Constants.HTTP_STATUS_CODE_200);
		RestClient.getPrettyResponsePrint(response);
		JsonPath js = RestClient.getJsonPath(response);
		
		String message = js.getString("_meta.message");
		System.out.println(message);
		Assert.assertEquals(message, "OK. Everything worked as expected.");
		
		ArrayList results=js.get("result");
		System.out.println(results.size());
		
		System.out.println(results.get(0)); // first array element
		
		Map<String,Object> firstUserData= (Map<String, Object>) results.get(0);
		
		for(Map.Entry<String, Object> entry: firstUserData.entrySet()) {
			System.out.println("Key = "+entry.getKey() +
					", Value = "+entry.getValue());
		}
	}

}
