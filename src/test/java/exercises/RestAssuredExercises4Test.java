package exercises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;

class RestAssuredExercises4Test {

    private static RequestSpecification requestSpec;


    @BeforeAll
    static void setUp() {
        createRequestSpecification();
        retrieveOAuthToken();
    }


    static void createRequestSpecification() {

        requestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(9876).
                setBasePath("/api/f1").
                build();
    }

    /*******************************************************
     * Request an authentication token through the API
     * and extract the value of the access_token field in
     * the response to a String variable.
     * Use preemptive Basic authentication:
     * username = oauth
     * password = gimmeatoken
     * Use /oauth2/token
     ******************************************************/

    private static String accessToken;

    static void retrieveOAuthToken() {
        String basicAuth = given().spec(requestSpec)
                .params("basicAuth", "{\n" +
                        "      \"username\": \"oauth\",\n" +
                        "      \"password\": \"gimmeatoken\"\n" +
                        "    }")
                .when().log().all()
                .get("/oauth2/token").getBody().asString();
        accessToken = from(basicAuth).get("access_token").toString();

    }

    /*******************************************************
     * Request a list of payments for this account and check
     * that the number of payments made equals 4.
     * Use OAuth2 authentication with the previously retrieved
     * authentication token.
     * Use /payments
     * Value to be retrieved is in the paymentsCount field
     ******************************************************/

    @Test
    void checkNumberOfPayments() {
        given().
                spec(requestSpec).
                when().
                then();
    }

    /*******************************************************
     * Request the list of all circuits that hosted a
     * Formula 1 race in 2014 and check that this request is
     * answered within 100 ms
     * Use /2014/circuits.json
     ******************************************************/

    @Test
    void checkResponseTimeFor2014CircuitList() {

        given().
                spec(requestSpec).
                when().
                then();
    }
}
