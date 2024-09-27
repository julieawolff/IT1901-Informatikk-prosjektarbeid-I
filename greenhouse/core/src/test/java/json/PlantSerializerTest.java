package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Plant;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The PlantSerializerTest class contains JUnit tests for serializing Plant objects
 * using the Jackson ObjectMapper.
 */
public class PlantSerializerTest {

  private static ObjectMapper mapper;
  private static Plant p;
  private static Plant p2;
  private static Date date;

  /**
   * Initializes the necessary resources and objects for the test class before executing any tests.
   * This method sets up the Jackson ObjectMapper with the GreenhouseModule and creates sample Plant objects.
   */
  @BeforeAll
  public static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new GreenhouseModule());

    date = Plant.fromStringToDate("2023-01-01 00:00:00");
    p = new Plant("Julie","Seed",4,date);
    p2 = new Plant("Anine","Young plant",5,date);
  }

  /**
   * Tests the serialization of individual Plant objects using the Jackson ObjectMapper.
   */
  @Test
  public void testSerializerPlant() {
    try {
      assertEquals("{\"Name\":\"Julie\",\"Phase\":\"Seed\",\"Water interval\":\"4\",\"Last watered\":\"2023-01-01 00:00:00\",\"Creation date\":\"2023-01-01 00:00:00\"}", mapper.writeValueAsString(p));
      assertEquals("{\"Name\":\"Anine\",\"Phase\":\"Young plant\",\"Water interval\":\"5\",\"Last watered\":\"2023-01-01 00:00:00\",\"Creation date\":\"2023-01-01 00:00:00\"}", mapper.writeValueAsString(p2));
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }
}
