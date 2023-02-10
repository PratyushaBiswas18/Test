package methodDef;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	
	@Test(dataProvider = "BooksData", priority=1)
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response =given().header("Content-Type", "application/json").
		body(payload.AddBook(isbn,aisle)).
		when().post("Library/Addbook.php")
		.then().assertThat().statusCode(200).body("Msg", equalTo("successfully added")).extract().response().asString();
		
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@Test(dataProvider = "BooksData", priority=2)
	public void deleteBook(String isbn, String aisle) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().header("Content-Type","text/plain").
		body(payload.DeleteBook(isbn,aisle)).
		when().post("Library/DeleteBook.php")
		.then().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted")).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		System.out.println(js.get("msg").toString());
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"bcwedw","29236"},{"yosew","98798"},{"kdhos","43522"}};
	}

}
