package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Plant;
import core.PlantOverview;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The PlantOverviewDeserializerTest class contains JUnit tests for deserializing PlantOverview objects
 * using the Jackson ObjectMapper.
 */
public class PlantOverviewDeserializerTest {

  private static ObjectMapper mapper;
  private final String plants = "{\"Plants\":[{\"Name\":\"Julie\",\"Phase\":\"Seed\",\"Water interval\":\"4\",\"Last watered\":\"2023-01-02 00:00:00\",\"Creation date\":\"2023-01-01 00:00:00\"},{\"Name\":\"Anine\",\"Phase\":\"Young plant\",\"Water interval\":\"5\",\"Last watered\":\"2023-01-02 00:00:00\",\"Creation date\":\"2023-01-01 00:00:00\"}]}";

  /**
   * Initializes the Jackson ObjectMapper with the GreenhouseModule before running any tests.
   */
  @BeforeAll
  public static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new GreenhouseModule());
  }

  /**
   * Test the deserialization of JSON data into a `PlantOverview` object with correct formatting.
   */
  @Test
  public void testValidFormat() {
    PlantOverview overview = new PlantOverview();
    try {
        // Deserialize JSON data into a PlantOverview object
        overview = mapper.readValue(plants, PlantOverview.class);
    } catch (JsonProcessingException e) {
        System.err.println(e.toString());
    }

    Iterator<Plant> plantIterator = overview.getPlantOverview().iterator();
    assertTrue(plantIterator.hasNext());
    PlantPersistenceTest.checkPlant(plantIterator.next(), "Julie", "Seed", 4, Plant.fromStringToDate("2023-01-01 00:00:00"), Plant.fromStringToDate("2023-01-02 00:00:00"));
    assertTrue(plantIterator.hasNext());
    PlantPersistenceTest.checkPlant(plantIterator.next(), "Anine", "Young plant", 5, Plant.fromStringToDate("2023-01-01 00:00:00"), Plant.fromStringToDate("2023-01-02 00:00:00"));
    assertFalse(plantIterator.hasNext());
  }

  /**
   * Test case to verify behavior when the JSON input does not represent an ObjectNode.
   * Expects the result to be null, indicating that the input is not a valid representation of PlantOverview.
   */
  @Test
  public void testNonObjectNode() {
    String jsonInput = "[{\"name\":\"PlantA\"}, {\"name\":\"PlantB\"}]";
    try {
      PlantOverview result = mapper.readValue(jsonInput, PlantOverview.class);
      assertNull(result);
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Test case to verify behavior when the "Plants" field in JSON is not an ArrayNode.
   * Expects an empty list of Plant objects in the resulting PlantOverview.
   */
  @Test
  public void testNonArrayNode() {
    String jsonInputNonArrayPlants = "{\"Plants\":\"someValue\"}";
    try {
      PlantOverview resultNonArrayPlants = mapper.readValue(jsonInputNonArrayPlants, PlantOverview.class);
      assertTrue(resultNonArrayPlants.getPlantOverview().isEmpty());
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Test case to verify behavior when a Plant object in the JSON array is malformed or contains invalid fields.
   * Expects an empty list of Plant objects in the resulting PlantOverview.
   */
  @Test
  public void testNullPlant() {
    String jsonInputWithNullPlant = "{\"Plants\":[{\"someInvalidField\":\"invalidValue\"}]}";
    try {
      PlantOverview resultNullPlant = mapper.readValue(jsonInputWithNullPlant, PlantOverview.class);
      assertTrue(resultNullPlant.getPlantOverview().isEmpty());
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }
}

