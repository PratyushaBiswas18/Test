package methodDef;

import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

/*
 * Problem Statement:
 * 1. Print No of courses returned by API
 * 
 * 2.Print Purchase Amount
 * 
 * 3. Print Title of the first course
 * 
 * 4. Print All course titles and their respective Prices
 * 
 * 5. Print no of copies sold by RPA Course
 * 
 * 6. Verify if Sum of all Course prices matches with Purchase Amount
 */

public class ComplexJsonParse {
	public static void main(String args[]) {

		JsonPath js = new JsonPath(payload.CoursePrice());
		// 1
		int courseElement = js.getInt("courses.size()"); // course is an array
		System.out.println("courseElement: " + courseElement);

		// 2
		int purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println("Total amount: " + purchaseAmt);

		// 3
		System.out.println("First Course title: " + js.getString("courses[0].title"));

		// 4
		System.out.println("------ Course Titles and their Prices: -------");
		for (int i = 0; i < courseElement; i++) {
			System.out.println("Course: " + js.get("courses[" + i + "].title").toString());
			System.out.println("Price: " + js.getInt("courses[" + i + "].price") + "\n");

		}

		System.out.println("-----------------------------");

		// 5
		for (int i = 0; i < courseElement; i++) {

			if ("RPA".equals(js.getString("courses[" + i + "].title"))) {
				System.out.println("Copies sold by RPA: " + js.getInt("courses[" + i + "].copies"));
				break;
			}
		}

		// 6
		int sum = 0;
		for (int i = 0; i < courseElement; i++) {
			
			sum = sum + (js.getInt("courses[" + i + "].copies")*js.getInt("courses[" + i + "].price"));
		}
		
		if (sum == purchaseAmt)
			System.out.println("Sum of all Course prices matches with Purchase Amount");
		else
			System.out.println("Sum of all Course prices does NOT matche with Purchase Amount");
		
		//Another way
		Assert.assertEquals(sum, purchaseAmt);
	}

}
