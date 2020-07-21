package com.qa.client;

import java.util.List;

import com.qa.util.TestUtil;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * 
 * @author asingh6766
 *
 */
public class RestClient {

	
	// GET PUT POST DELETE 
	public static Response doGet(String contentType,String baseURI, String basePath, String token,boolean log) {
		RestClient.setBaseURI(baseURI);
		RequestSpecification  request = RestClient.createRequest(contentType, token, log);
		return RestClient.getResponse("GET", request, basePath);
	}

	public static Response doPost(String contentType,String baseURI, String basePath, String token,boolean log, Object obj) {
		RestClient.setBaseURI(baseURI);
		RequestSpecification  request = RestClient.createRequest(contentType, token, log);
	    request.body(TestUtil.getSerializedJson(obj));
        return RestClient.getResponse("POST", request, basePath);
        
	}

	public static Response doPut(String contentType,String baseURI, String basePath, String token,boolean log, Object obj) {
		RestClient.setBaseURI(baseURI);
		RequestSpecification  request = RestClient.createRequest(contentType, token, log);
	    request.body(TestUtil.getSerializedJson(obj));
        return RestClient.getResponse("PUT", request, basePath);

	}

	public static Response doDelete(String contentType,String baseURI, String basePath, String token,boolean log) {
		RestClient.setBaseURI(baseURI);
		RequestSpecification  request = RestClient.createRequest(contentType, token, log);
	    return RestClient.getResponse("DELETE", request, basePath);

	}
	
	// Generic API methods
	/**
	 * this method is used to set the base uri
	 * @param baseURI
	 */
	
	public static void setBaseURI(String baseURI) {
		
		RestAssured.baseURI = baseURI;
		
	}

	/**
	 * this method is used to create request
	 * @param contentType
	 * @param token
	 * @param log
	 * @return
	 */
	public static RequestSpecification createRequest(String contentType, String token, boolean log) {
		RequestSpecification request;
		if(log) {
			request = RestAssured.given().log().all();
		}else {
		 request = RestAssured.given();
		}
		if(token!=null) {
    	   request.header("Authorization","Bearer "+token);
          }
		if(contentType.equalsIgnoreCase("JSON")) {
			request.contentType(ContentType.JSON);
		}
		else if(contentType.equalsIgnoreCase("XML")){
		request.contentType(ContentType.XML);
		}
		else if(contentType.equalsIgnoreCase("TEXT")){
			request.contentType(ContentType.TEXT);
			}
		return request;
		}
	
	
	public static Response getResponse(String httpMethod, RequestSpecification request, String basePath ) {
		return executeAPI(httpMethod, request, basePath);
		
	}
	/**
	 * this method is used to execute the API on the basis of HTTP Method GET,PUT,POST,DELETE
	 * @param httpMethod
	 * @param request
	 * @param basePath
	 * @return
	 */
	private static Response executeAPI(String httpMethod, RequestSpecification request, String basePath ) {
		Response response = null;
		
		switch (httpMethod) {
		case "GET":
			response = request.get(basePath);
			break;
		case "POST":
			response = request.post(basePath);
			break;
		case "PUT":
			response = request.put(basePath);
			break;
		case "DELETE":
			response = request.delete(basePath);
			break;

		default: System.out.println("Please pass the correct http method");
			break;
		}
		return response;
	}
	
	// generic method for response
	
	public static JsonPath getJsonPath(Response response) {
		
		return response.jsonPath();
	}
	
	public static int getStatusCode(Response response) {
		return response.getStatusCode();
		
	}
	
	public static String getHeaderValue(Response response, String headerName) {
		return response.getHeader(headerName);
		
	}
	
	public static int getHeaderCount(Response response) {
			Headers headers =  response.getHeaders();
		     return headers.size();
	}
	
	public static List<Header> getResponseHeaders(Response response) {
		Headers headers = response.getHeaders();
		  List<Header> headersList = headers.asList();
		  return headersList;
	}
	
	public static void getPrettyResponsePrint(Response response) {
		System.out.println("====== Response String in Pretty Format ==========");
		response.prettyPrint();
	}
	
}
