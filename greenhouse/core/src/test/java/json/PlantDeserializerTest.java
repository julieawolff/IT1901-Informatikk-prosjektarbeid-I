package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import core.Plant;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


/**
 * The PlantDeserializerTest class contains JUnit tests for deserializing Plant objects
 * using the Jackson ObjectMapper.
 */
public class PlantDeserializerTest {
  
  private static ObjectMapper mapper;

  /**
   * Initializes the Jackson ObjectMapper with the GreenhouseModule before running any tests.
   */
  @BeforeAll
  public static void setUp() {
    mapper = new ObjectMapper();
    mapper.registerModule(new GreenhouseModule());
  }

  /**
   * Test deserialization of a valid JSON representation of a Plant object.
   * This test ensures that a valid JSON input is correctly deserialized into a Plant object.
   *
   * @throws JsonProcessingException if a JSON processing error occurs.
   */
  @Test
  public void testValidPlant() {
    String json = "{\"Name\": \"Frank\", \"Phase\": \"Seed\", \"Water interval\": \"3\", \"Last watered\": \"2023-01-02 00:00:00\", \"Creation date\": \"2023-01-01 00:00:00\"}";
    try {
      Plant plant = mapper.readValue(json, Plant.class);
      assertEquals("Frank", plant.getName());
      assertEquals("Seed", plant.getPhase());
      assertEquals(3, plant.getWaterInterval());
      assertEquals("2023-01-02 00:00:00", Plant.toStringDate(plant.getLastWatered()));
      assertEquals("2023-01-01 00:00:00", Plant.toStringDate(plant.getCreationDate()));
    } catch (JsonProcessingException e) {
      System.err.println(e.toString()); 
    }
  }

  /**
   * Test deserialization of an invalid JSON representation of a Plant object with a missing name field.
   * This test checks that the deserializer handles an invalid Plant object with a missing "Name" field correctly.
   *
   * @throws JsonProcessingException if a JSON processing error occurs.
   */
  @Test
  public void testPlantMissingName() {
    String json = "{\"Name\": \"\", \"Phase\": \"Seed\", \"Water interval\": \"3\", \"Last watered\": \"2023-01-02 00:00:00\", \"Creation date\": \"2023-01-01 00:00:00\"}";
    try {
      Plant plant = mapper.readValue(json, Plant.class);
      assertNull(plant);
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Test deserialization of an invalid JSON representation of a Plant object with a missing phase field.
   * This test checks that the deserializer handles an invalid Plant object with a missing "Phase" field correctly.
   *
   * @throws JsonProcessingException if a JSON processing error occurs.
   */
  @Test
  public void testPlantMissingPhase() {
    String json = "{\"Name\": \"Tommy\", \"Phase\": \"\", \"Water interval\": \"3\", \"Last watered\": \"2023-01-02 00:00:00\", \"Creation date\": \"2023-01-01 00:00:00\"}";
    try {
      Plant plant = mapper.readValue(json, Plant.class);
      assertNull(plant);
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }
  /**
   * Test deserialization of an invalid JSON representation of a Plant object with a missing water interval field.
   * This test checks that the deserializer handles an invalid Plant object with a missing "Water interval" field correctly.
   *
   * @throws JsonProcessingException if a JSON processing error occurs.
   */
  @Test
  public void testPlantMissingWaterInterval() {
    String json = "{\"Name\": \"Tommy\", \"Phase\": \"Seed\", \"Water interval\": \"\", \"Last watered\": \"2023-01-02 00:00:00\", \"Creation date\": \"2023-01-01 00:00:00\"}";
    try {
      Plant plant = mapper.readValue(json, Plant.class);
      assertNull(plant);
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Test case for an invalid plant with three missing fields (Name, Phase, and Water interval).
   * The provided JSON represents a plant with empty values for Name, Phase, and Water interval.
   * The test checks whether the plant object is null after attempting to deserialize the JSON.
   */
  @Test
  public void testPlantThreeMissingFields() {
    String json = "{\"Name\": \"\", \"Phase\": \"\", \"Water interval\": \"\", \"Last watered\": \"2023-01-02 00:00:00\", \"Creation date\": \"2023-01-01 00:00:00\"}";
    try {
      Plant plant = mapper.readValue(json, Plant.class);
      assertNull(plant);
    } catch (JsonProcessingException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Test deserialization from an invalid JSON node for a Plant object.
   * This test checks that the deserializer correctly handles an invalid JSON node as input.
   */
  @Test
  public void testInvalidJsonNode() {
    TextNode jsonNode = new TextNode("Hello, World");
    PlantDeserializer deserializer = new PlantDeserializer();
    Plant result = deserializer.deserialize(jsonNode);
    assertNull(result);
  }
}
