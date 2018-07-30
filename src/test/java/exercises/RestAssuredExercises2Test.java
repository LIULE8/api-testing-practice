package exercises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;


class RestAssuredExercises2Test {

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
     * Use junit-jupiter-params for @ParameterizedTest that
     * specifies in which country
     * a specific circuit can be found (specify that Monza
     * is in Italy, for example)
     ******************************************************/
    private static Stream<Arguments> circuitIdAndCountry() {
        return Stream.of(
                Arguments.of("monza", "Italy")
        );
    }

    /*******************************************************
     * Use junit-jupiter-params for @ParameterizedTest that specifies for all races
     * (adding the first four suffices) in 2015 how many
     * pit stops Max Verstappen made
     * (race 1 = 1 pitstop, 2 = 3, 3 = 2, 4 = 2)
     ******************************************************/
    private static Stream<Arguments> raceAndTotal() {
        return Stream.of(
                Arguments.of("1", "1"),
                Arguments.of("2", "3"),
                Arguments.of("3", "2"),
                Arguments.of("4", "2")
        );
    }

    /*******************************************************
     * Request data for a specific circuit (for Monza this
     * is /circuits/monza.json)
     * and check the country this circuit can be found in
     ******************************************************/

    @ParameterizedTest
    @MethodSource("circuitIdAndCountry")
    void checkCountryForCircuit(String circuitId, String country) {
        given().
                pathParam("circuitId", circuitId).
                spec(requestSpec).
                when().log().all().get("/circuits/{circuitId}.json").
                then().log().all().
                body("MRData.CircuitTable.Circuits[0].Location.country", equalTo(country));
    }

    /*******************************************************
     * Request the pitstop data for the first four races in
     * 2015 for Max Verstappen (for race 1 this is
     * /2015/1/drivers/max_verstappen/pitstops.json)
     * and verify the number of pit stops made
     ******************************************************/

    @ParameterizedTest
    @MethodSource("raceAndTotal")
    void checkNumberOfPitstopsForMaxVerstappenIn2015(String number, String total) {
        given().pathParam("n", number).
                spec(requestSpec).
                when().get("/2015/{n}/drivers/max_verstappen/pitstops.json").
                then().log().all()
                .body("MRData.total", equalTo(total));
    }
}