package exercises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class RestAssuredExercises5Test {

	private static RequestSpecification requestSpec;

	@BeforeAll
	 static void createRequestSpecification() {

		requestSpec = new RequestSpecBuilder().
			setBaseUri("http://localhost").
			setPort(9876).
			build();
	}
		
	/*******************************************************
	 * Get the list of speed records set by street legal cars
	 * use /xml/speedrecords
	 * Check that the third speed record in the list was set
	 * in 1955
	 ******************************************************/
	
	@Test
	 void checkThirdSpeedRecordWasSetIn1955() {
		String json = given().
				spec(requestSpec).
				when().log().all().get("/xml/speedrecords").getBody().asString();
		XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, json);
		String year = xmlPath.getString("speedRecords.car[2].year");
		Assertions.assertEquals("1955", year);

	}
	
	/*******************************************************
	 * Get the list of speed records set by street legal cars
	 * use /xml/speedrecords
	 * Check that the fourth speed record in the list was set
	 * by an Aston Martin
	 ******************************************************/
	
	@Test
	 void checkFourthSpeedRecordWasSetbyAnAstonMartin() {
		String json = given().
				spec(requestSpec).
				when().log().all().get("/xml/speedrecords").getBody().asString();
		XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, json);
		Node node = xmlPath.getNode("speedRecords.car[3]");
		Assertions.assertEquals("Aston Martin", node.attributes().get("make"));
	}
	
	/*******************************************************
	 * Get the list of speed records set by street legal cars
	 * use /xml/speedrecords
	 * Check that three speed records have been set by cars
	 * from the UK
	 ******************************************************/
	
	@Test
	 void checkThreeRecordsHaveBeenSetByCarsFromTheUK() {
		
		given().
			spec(requestSpec).
		when().
		then();
	}
	
	/*******************************************************
	 * Get the list of speed records set by street legal cars
	 * use /xml/speedrecords
	 * Check that four speed records have been set by cars
	 * from either Italy or Germany
	 ******************************************************/
	
	@Test
	 void checkFourRecordsHaveBeenSetByCarsFromEitherItalyOrGermany() {
		
		given().
			spec(requestSpec).
		when().
		then();
	}
	
	/*******************************************************
	 * Get the list of speed records set by street legal cars
	 * use /xml/speedrecords
	 * Check that two speed records have been set by cars
	 * whose make ends on 'Benz'
	 ******************************************************/
	
	@Test
	 void checkTwoRecordsHaveBeenSetByCarsWhoseMakeEndOnBenz() {
		
		given().
			spec(requestSpec).
		when().
		then();
	}
}