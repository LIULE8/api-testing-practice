package exercises;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

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
        String xml = given().
                spec(requestSpec).
                when().log().all().get("/xml/speedrecords").getBody().asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, xml);
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

        String xml = given().
                spec(requestSpec).
                when().log().all().get("/xml/speedrecords").asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, xml);
        List<Node> nodes = xmlPath.getList("speedRecords.car", Node.class);
        long count = nodes.stream().filter(node -> node.getAttribute("country").equals("UK")).count();
        Assertions.assertEquals(3L, count);
    }

    /*******************************************************
     * Get the list of speed records set by street legal cars
     * use /xml/speedrecords
     * Check that four speed records have been set by cars
     * from either Italy or Germany
     ******************************************************/

    @Test
    void checkFourRecordsHaveBeenSetByCarsFromEitherItalyOrGermany() {
        String xml = given().
                spec(requestSpec).
                when().log().all().get("/xml/speedrecords").asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, xml);
        List<Node> nodes = xmlPath.getList("speedRecords.car", Node.class);
        long count1 = nodes.stream().filter(node -> node.getAttribute("country").equals("Italy")).count();
        long count2 = nodes.stream().filter(node -> node.getAttribute("country").equals("Germany")).count();
        Assertions.assertTrue(!(count1 == 4 || count2 == 4));

    }

    /*******************************************************
     * Get the list of speed records set by street legal cars
     * use /xml/speedrecords
     * Check that two speed records have been set by cars
     * whose make ends on 'Benz'
     ******************************************************/

    @Test
    void checkTwoRecordsHaveBeenSetByCarsWhoseMakeEndOnBenz() {
        String xml = given().
                spec(requestSpec).
                when().log().all().get("/xml/speedrecords").asString();
        XmlPath xmlPath = new XmlPath(XmlPath.CompatibilityMode.HTML, xml);
        List<Node> nodes = xmlPath.getList("speedRecords.car", Node.class);
        long count = nodes.stream().filter(node -> node.getAttribute("make").endsWith("Benz")).count();
        Assertions.assertEquals(2L, count);
    }
}