package exercises;


import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Tag("demo")
class RestAssuredExamplesXml {

    @Test
    void checkCountryForFirstCar() {

        given().
        when().
            get("http://path.to/cars/xml").
        then().
            assertThat().
            body("cars.car[0].country", equalTo("Italy"));
    }

    @Test
    void checkYearForLastCar() {

        given().
        when().
            get("http://path.to/cars/xml").
        then().
            assertThat().
            body("cars.car[-1].year", equalTo("2012"));
    }

    @Test
    void checkModelForSecondCar() {

        given().
        when().
            get("http://path.to/cars/xml").
        then().
            assertThat().
            body("cars.car[1].@model", equalTo("DB11"));
    }

    @Test
    void checkTheListContainsOneJapaneseCar() {

        given().
        when().
            get("http://path.to/cars/xml").
        then().
            assertThat().
            body("cars.car.findAll{it.country=='Japan'}.size()", equalTo(1));
    }

    @Test
    void checkTheListContainsTwoCarsWhoseMakeStartsWithAnA() {

        given().
        when().
            get("http://path.to/cars/xml").
        then().
            assertThat().
            body("cars.car.@make.grep(~/A.*/).size()", equalTo(2));
    }
}

