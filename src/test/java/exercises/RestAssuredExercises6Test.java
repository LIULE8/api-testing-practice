package exercises;

import dataentities.Car;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

class RestAssuredExercises6Test {

	private static RequestSpecification requestSpec;


	@BeforeAll
	static void setUp() {

		requestSpec = new RequestSpecBuilder().
				setBaseUri("http://localhost").
				setPort(9876).
				setContentType(ContentType.JSON).
				build();
	}


	/*******************************************************
	 * Create a new Car object that represents a 2012 Ford Focus
	 * POST this object to /cars/postcar
	 * Verify that the response HTTP status code is equal to 200
	 ******************************************************/

	@Test
	 void checkThatPostingA2012FordFocusReturnsHttp200() {

		given().
			spec(requestSpec).body(new Car("Ford","Focus",2012)).
		when().post("/car/postcar").
		then().log().all().statusCode(HttpStatus.SC_OK);
	}

	/*******************************************************
	 * Perform a GET to /cars/getcar/alfaromeogiulia
	 * Store the response in a Car object
	 * Verify, using that object, that the model year = 2016
	 * Use the standard Assert.assertEquals(expected,actual)
	 * as provided by JUnit for the assertion
	 ******************************************************/

	@Test
	 void checkThatRetrievingAnAlfaRomeoGiuliaShowsModelYear2016() {

		String json = given().
				spec(requestSpec).
				when().log().all().get("/car/getcar/alfaromeogiulia").asString();
		String year = from(json).get("year").toString();
		// Put your assert here
		Assertions.assertEquals("2016",year);
	}
}