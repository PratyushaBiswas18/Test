package methodDef;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.payload;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Validate if add place aPI is working as expected
		
		//given - all input details
		// when - submit API - resource, http method
		// then - validate the response
		
		
		RestAssured.baseURI ="https://rahulshettyacademy.com";
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(payload.AddPlace())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("server", "Apache/2.4.41 (Ubuntu)");
		
		
		//Add place -> Update Place with NEw Adress -> Get place to validate if new address is present in response
		

	}

}
