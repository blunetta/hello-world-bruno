package glueCode;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class cucumberSteps {
	
	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
	private static Response response;
	private static String responseBody;
	private static int statusCode;	
	private static int companySize;
	
	
	@Given("A list of Users are available")
	public void a_list_of_Users_are_available() {
		// Specify URL
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
		// RequestObject
		RequestSpecification httpRequest = RestAssured.given();
		// ResponseObject
		Response response = httpRequest.request(Method.GET, "/users");

		responseBody = response.getBody().asString();
		// System.out.println("Response body is...: " + responseBody);
	}

	@When("StatusCode should be sucess")
	public void statuscode_should_be_sucess() {
		statusCode = 0; 
		statusCode = response.getStatusCode();
		// verification point of status code
		Assert.assertEquals(statusCode, 200);
		System.out.println("Status code is...: " + statusCode);	
	} 

	@Then("All users must have a name, username, and email.")
	public void all_users_must_have_a_name_username_and_email() {
		
		given().get("https://jsonplaceholder.typicode.com/users").then().
		statusCode(200).
		body("[0].id", equalTo(1)).
		body("name", notNullValue()).
		body("username", notNullValue()).
		body("email", notNullValue()).	
		body("[0].company.name", equalTo("Romaguera-Crona"));
		}

	@Then("Their Email must be valid.")
	public void their_Email_must_be_valid() {
		
		response = given().get("https://jsonplaceholder.typicode.com/users");
		// Get JSON Representation from Response Body 
		JsonPath jsonPathEvaluator = response.jsonPath();
		// Get specific element from JSON document 
		ArrayList<String> emailList = jsonPathEvaluator.get("email");
		for (String email : emailList) {
			Assert.assertTrue(isValidEmailAddress(email));
			}
		}
		
	@Then("Their Company catchphrase must have less than characters.")
	public void their_Company_catchphrase_must_have_less_than_characters() {
		given().get("https://jsonplaceholder.typicode.com/users").then().
		assertThat().body("company.name.size()", lessThan(50));
	}




}
