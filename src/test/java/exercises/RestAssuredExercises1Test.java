package exercises;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static net.bytebuddy.matcher.ElementMatchers.failSafe;
import static net.bytebuddy.matcher.ElementMatchers.is;
import static net.bytebuddy.matcher.ElementMatchers.isEquals;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;


class RestAssuredExercises1Test {

    private static RequestSpecification requestSpec;

    @BeforeAll
    static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                setBasePath("/api/f1").
                build();
    }

    /*******************************************************
     * Send a GET request to /2016/drivers.json
     * and check that the response has HTTP status code 200
     ******************************************************/

    @Test
    void checkResponseCodeForCorrectRequest() {
        given().
                spec(requestSpec)
                .when().log().uri().get("/2016/drivers.json")
                .then().log().all().assertThat()
                .statusCode(HttpStatus.SC_OK);
    }

    /*******************************************************
     * Send a GET request to /incorrect.json
     * and check that the answer has HTTP status code 404
     ******************************************************/

    @Test
    void checkResponseCodeForIncorrectRequest() {

        given().
                spec(requestSpec).
                when().log().all().get("/incorrect.json")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    /*******************************************************
     * Send a GET request to /2016/drivers.json
     * and check that the response is in JSON format
     ******************************************************/

    @Test
    void checkResponseContentTypeJson() {

        given().
                spec(requestSpec).
                when().log().all().get("/2016/drivers.json")
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK);
    }

    /***********************************************
     * Retrieve circuit information for the first race
     * of the 2014 season and check the circuitId equals
     * albert_park
     * Use /2014/1/circuits.json
     **********************************************/

    @Test
    void checkTheFirstRaceOf2014WasAtAlbertPark() {
        given().
                spec(requestSpec).
                when().log().all().get("/2014/1/circuits.json").
                then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .contentType(ContentType.JSON)
                .body("MRData.CircuitTable.season", equalTo("2014"))
                .body("MRData.CircuitTable.Circuits.circuitId", hasItems("albert_park"));

    }

    /***********************************************
     * Retrieve the list of circuits for the 2014
     * season and check that it contains silverstone
     * Use /2014/circuits.json
     **********************************************/

    @Test
    void checkThereWasARaceAtSilverstoneIn2014() {
        given().
                spec(requestSpec).
                when().log().all().get("/2014/circuits.json").
                then().log().all().contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body(containsString("silverstone"));
    }

    /***********************************************
     * Retrieve the list of circuits for the 2014
     * season and check that it does not contain
     * nurburgring
     * USe /2014/circuits.json
     **********************************************/

    @Test
    void checkThereWasNoRaceAtNurburgringIn2014() {
        given().
                spec(requestSpec).
                when().log().all().get("/2014/circuits.json").
                then().log().all().contentType(ContentType.JSON)
                .statusCode(HttpStatus.SC_OK)
                .body(not(containsString("nurburgring")));
    }
}