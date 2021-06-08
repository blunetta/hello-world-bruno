package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class steps {

	private static Response response;
	private String responseBody;
	private int statusCode;
	private int companySize;
	
	 public boolean isValidEmailAddress(String email) {
         String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
         java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
         java.util.regex.Matcher m = p.matcher(email);
         return m.matches();
  }

	@Test
	public void Test01() {
		// Specify URL
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
		// RequestObject
		RequestSpecification httpRequest = RestAssured.given();
		// ResponseObject
		Response response = httpRequest.request(Method.GET, "/users");

		responseBody = response.getBody().asString();
		//System.out.println("Response body is...: " + responseBody);

		//System.out.println(response.statusLine());
		//System.out.println(response.getHeader("content-type"));
		System.out.println(response.getTime());
		
		// Status Code Validation
		statusCode = response.getStatusCode();
		// verification point of status code
		Assert.assertEquals(statusCode, 200);
		System.out.println("Status code is...: " + statusCode);	
		
	}
	
	@Test
	public void test2() {
		

		given().get("https://jsonplaceholder.typicode.com/users").then().
		statusCode(200).
		body("[0].id", equalTo(1)).
		body("name", notNullValue()).
		body("username", notNullValue()).
		body("email", notNullValue()).
		
		//body("[0].username", equalTo("Bret")).
		//body("[0].email", equalTo("Sincere@april.biz")).
		body("[0].company.name", equalTo("Romaguera-Crona"));
		
		given().get("https://jsonplaceholder.typicode.com/users").then().
		assertThat().body("company.name.size()", lessThan(50));
		//assertThat().body("email", is(email));
		//assertThat().body("[0].company.name", hasSize(15));
		//body("size()", lessThan(16));
		//System.out.println();
		
		response = given().get("https://jsonplaceholder.typicode.com/users");
		// Get JSON Representation from Response Body 
		JsonPath jsonPathEvaluator = response.jsonPath();
		// Get specific element from JSON document 
		ArrayList<String> emailList = jsonPathEvaluator.get("email");
		for (String email : emailList) {
			Assert.assertTrue(isValidEmailAddress(email));
			}
		}
	
		@Test
		public void test3() {
	
		}

}
