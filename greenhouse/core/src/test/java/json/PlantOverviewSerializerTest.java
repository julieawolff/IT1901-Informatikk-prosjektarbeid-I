package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Plant;
import core.PlantOverview;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The PlantOverviewSerializerTest class contains JUnit tests for serializing PlantOverview objects
 * using the Jackson ObjectMapper.
 */
public class PlantOverviewSerializerTest {

  private static ObjectMapper mapper;
  private final String plants = "{\"Plants\":[{\"Name\":\"Julie\",\"Phase\":\"Seed\",\"Water interval\":\"4\",\"Last watered\":\"2023-01-01 00:00:00\",\"Creation date\":\"2023-01-01 00:00:00\"},{\"Name\":\"Anine\",\"Phase\":\"Young plant\",\"Water interval\":\"5\",\"Last watered\":\"2023-01-01 00:00:00\",\"Creation date\":\"2023-01-01 00:00:00\"}]}";
  private static Plant p;
  private static Plant p2;
  private static Date date;
  private static PlantOverview o;
  
  /**
   * Initializes the necessary resources and objects for the test class before executing any tests.
   * This method sets up the Jackson ObjectMapper with the GreenhouseModule, creates sample Plant objects,
   * and populates a PlantOverview instance for testing serialization and deserialization.
   */
  @BeforeAll
  public static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new GreenhouseModule());

    date = Plant.fromStringToDate("2023-01-01 00:00:00");
    p = new Plant("Julie","Seed",4,date);
    p2 = new Plant("Anine","Young plant",5,date);
    o = new PlantOverview();
    o.addPlant(p);
    o.addPlant(p2);
  }
  /**
   * Tests the serialization of the entire PlantOverview using the Jackson ObjectMapper.
   */
  @Test
  public void testSerializerOverview() {
    try {
      assertEquals(plants, mapper.writeValueAsString(o));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
