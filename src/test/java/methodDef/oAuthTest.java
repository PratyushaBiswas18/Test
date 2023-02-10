package methodDef;
import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import files.ReusableMethods;

public class oAuthTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JsonPath js;
		/*
		 * WebDriver driver;
		 * 
		 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver();
		 */
		
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.findElement(By.xpath("//input[@name = 'identifier']")).sendKeys(email);
//		driver.findElement(By.xpath("//input[@name = 'identifier']")).sendKeys(Keys.ENTER);
//		Thread.sleep(10000);
//		
//		String codeUrl= driver.getCurrentUrl();
		String codeTemp ="4%2F0AWtgzh4pgCwReJVJFxrU7lSe2Ig_MyFL1uVcFpItTCw3sB_P1XxN_uXe3dpaeeOeyggy1A";
		String codeUrl = "https://rahulshettyacademy.com/getCourse.php?code="+codeTemp+"&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		//driver.close();
		String partialCode= codeUrl.split("code=")[1];
		String code = partialCode.split("&scope")[0];
					
		
		String accessTokenResponse= given().urlEncodingEnabled(false).
		queryParams("code", code).
		queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
		queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").
		queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").
		queryParams("grant_type", "authorization_code").
		when().log().all().
		post("https://www.googleapis.com/oauth2/v4/token").
		asString();	
		
		js = ReusableMethods.rawToJson(accessTokenResponse);
		String accessToken = js.getString("access_token");
		
		String response=given().queryParam("access_token", accessToken).
		when().log().all().
		get("https://rahulshettyacademy.com/getCourse.php").
		asString();	
		System.out.println(response);
		
	}

}
