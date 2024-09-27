package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * This class contains JUnit tests for the functionality of the Plant class.
 */
public class PlantTest {

  /**
   * Tests the first constructor of the Plant class.
   */
  @Test
  public void testFirstConstructor() {
    String name = "Solveig";
    String phase = "Grown plant";
    int waterInterval = 3;
    Date creationDate = Plant.fromStringToDate("2023-10-08 11:02:57");
    Date lastWatered = Plant.fromStringToDate("2023-10-14 11:02:57");

    Plant plant = new Plant(name, phase, waterInterval, creationDate, lastWatered);

    assertEquals(name, plant.getName());
    assertEquals(phase, plant.getPhase());
    assertEquals(waterInterval, plant.getWaterInterval());
    assertEquals(creationDate, plant.getCreationDate());
    assertEquals(lastWatered, plant.getLastWatered());
  }

  /**
   * Tests the second constructor of the Plant class.
   */
  @Test
  public void testSecondConstructor() {
    String name = "Sanne";
    String phase = "Seed";
    int waterInterval = 3;
    Date creationDate = Plant.fromStringToDate("2023-10-06 11:02:57");

    Plant plant = new Plant(name, phase, waterInterval, creationDate);

    assertEquals(name, plant.getName());
    assertEquals(phase, plant.getPhase());
    assertEquals(waterInterval, plant.getWaterInterval());
    assertEquals(creationDate, plant.getCreationDate());
    assertEquals(creationDate, plant.getLastWatered());
  }

  /**
   * Tests the third constructor of the Plant class.
   */
  @Test
  public void testThirdConstructor() {
    String name = "Sofie";
    String phase = "Grown plant";
    int waterInterval = 7;

    Plant plant2 = new Plant(name, phase, waterInterval);

    assertEquals(name, plant2.getName());
    assertEquals(phase, plant2.getPhase());
    assertEquals(waterInterval, plant2.getWaterInterval());
    assertNotNull(plant2.getCreationDate());
    assertNotNull(plant2.getLastWatered());
  }

  /**
   * Tests the copy constructor of the Plant class.
   */
  @Test
  public void testCopyConstructor() {
    Date creationDate = Plant.fromStringToDate("2023-10-06 11:02:57");
    Plant originalPlant = new Plant("Susanne", "Seed", 4, creationDate);

    Plant clonedPlant = new Plant(originalPlant);

    assertEquals(originalPlant.getName(), clonedPlant.getName());
    assertEquals(originalPlant.getPhase(), clonedPlant.getPhase());
    assertEquals(originalPlant.getWaterInterval(), clonedPlant.getWaterInterval());
    assertEquals(originalPlant.getCreationDate(), clonedPlant.getCreationDate());
    assertEquals(originalPlant.getLastWatered(), clonedPlant.getLastWatered());
  }

  /**
   * Tests the set methods of the Plant class.
   */
  @Test
  public void testSetMethod() {
    Plant plant = new Plant("Sol", "Young plant", 5);
    plant.setName("Synne");
    plant.setPhase("Seed");
    plant.setWaterInterval(2);
    plant.setLastWatered(Plant.fromStringToDate("2023-9-08 11:02:57"));
    assertEquals("Synne", plant.getName());
    assertEquals("Seed", plant.getPhase());
    assertEquals(2, plant.getWaterInterval());
    assertEquals(Plant.fromStringToDate("2023-9-08 11:02:57"), plant.getLastWatered());
    assertNotNull(plant.getCreationDate());
  }
  
  /**
   * Tests the toString method of the Plant class.
   */
  @Test
  public void testToString() {
    Date creationDate = Plant.fromStringToDate("2023-9-06 11:02:57");
    Plant plant = new Plant("Sofie", "Seed", 5, creationDate);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String expected = "Name: Sofie\nPhase: Seed\nWater interval: 5 day(s) \nLifespan: "+ Plant.daysBetween(plant.getCreationDate(), new Date()) + "\nCreation date: " + dateFormat.format(plant.getCreationDate()) + "\nLast watered: " + dateFormat.format(plant.getLastWatered());

    assertEquals(expected, plant.toString());
  }
  
  /**
   * Tests the toStringDate method of the Plant class.
   */
  @Test
  public void testToStringDate() {
    String expected = "2023-10-06 11:02:57";
    Date actual = Plant.fromStringToDate(expected);
    assertEquals(expected, Plant.toStringDate(actual));
  }

  /**
   * Tests the fromStringToDate method of the Plant class with an invalid date.
   */
  @Test
  public void testFromStringToDateWithInvalidDate() {
    String invalidDateString = "02.03.2002"; // Invalid format
    Date result = Plant.fromStringToDate(invalidDateString);
    assertNull(result); // Ensure that the result is null, indicating a ParseException was caught
  }

  /**
   * Tests the lifeSpan method of the Plant class.
   */
  @Test
  public void testLifeSpan() {
    Date today = new Date();
    Date threeDaysAgo = new Date(today.getTime() - 3 * 24 * 60 * 60 * 1000); // Three days ago 
    Plant plant = new Plant("Sara", "Grown plant", 3, threeDaysAgo);
    
    assertEquals(3, plant.lifeSpan());
  }

  /**
   * Tests the needsWater method of the Plant class.
   */
  @Test
  public void testNeedsWater() {
    Date today = new Date();
    Date twoDaysAgo = new Date(today.getTime() - 2 * 24 * 60 * 60 * 1000); // Two days ago
    Plant plant = new Plant("Sana", "Seed", 1, twoDaysAgo);
    Plant plant2 = new Plant("Daisy", "Young plant", 3, twoDaysAgo);
    assertTrue(plant.needsWater());
    assertFalse(plant2.needsWater());
  }
}
