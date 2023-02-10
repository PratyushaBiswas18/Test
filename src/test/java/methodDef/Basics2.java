package methodDef;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics2 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Validate if add place aPI is working as expected

		// given - all input details
		// when - submit API - resource, http method
		// then - validate the response

		// Add place -> Update Place with NEw Adress -> Get place to validate if new
		// address is present in response

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		// Add Place
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);

		// for parsing Json
		JsonPath js1 = ReusableMethods.rawToJson(response);
		String placeID = js1.getString("place_id");
		System.out.println("Place ID: " + placeID);

		// Update Place

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.UpdatePlace(placeID)).when().put("maps/api/place/update/json").then().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get Place

		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

		// for parsing Json
		js1 = ReusableMethods.rawToJson(getPlaceResponse);
		String updatedPlaceID = js1.getString("address");
		System.out.println("Place ID: " + updatedPlaceID);
		Assert.assertEquals("70 Summer walk, USA", updatedPlaceID);

	}
}
