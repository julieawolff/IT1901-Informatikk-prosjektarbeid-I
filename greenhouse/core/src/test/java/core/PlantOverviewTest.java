package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains JUnit tests for the functionality of the PlantOverview class.
 */
public class PlantOverviewTest {
  private PlantOverview plantOverview;
  private Plant plant;
  private Plant plant2;

  /**
   * Sets up the test environment before each test method is executed.
   */
  @BeforeEach
  public void setUp() {
    plantOverview = new PlantOverview();
    plant = new Plant("Jenny", "Seed", 7);
    plant2 = new Plant("Julie", "Seed",5);
  }

  /**
   * Tests the addPlant and deletePlant methods of PlantOverview.
   */
  @Test
  public void testAddAndDeletePlant() {
    plantOverview.addPlant(plant);
    assertTrue(plantOverview.getPlantOverview().contains(plant));

    plantOverview.deletePlant("Jenny");
    assertTrue(plantOverview.getPlantOverview().isEmpty());
    plantOverview.addPlant(plant);

    Collection<Plant> beforeDeleting = plantOverview.getPlantOverview();
    plantOverview.deletePlant("Juni");
    assertEquals(beforeDeleting, plantOverview.getPlantOverview());
  }

  /**
   * Tests the checkAddPlant method of PlantOverview.
   */
  @Test
  public void testCheckAddPlant() {
    plantOverview.addPlant(plant);
    
    // Ensure that adding a plant with the same name results in an exception
    Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> {
        plantOverview.addPlant(new Plant("Jenny", "Seed", 4));    
    });
    
    // Ensure that adding a plant without a name results in an exception
    Throwable exception2 = assertThrows(IllegalArgumentException.class, () -> {
        plantOverview.addPlant(new Plant(null, "Seed", 4));    
    });

    // Ensure that adding a plant with empty string as name results in an exception
    Throwable exception3 = assertThrows(IllegalArgumentException.class, () -> {
        plantOverview.addPlant(new Plant("", "Seed", 4));    
    });

    // Ensure that adding a plant with no phase results in an exception
    Throwable exception4 = assertThrows(IllegalArgumentException.class, () -> {
                plantOverview.addPlant(new Plant("Jennina", null, 2));    
    });
    
    // Check if the exception messages match the expected messages
    assertEquals("There already exists a plant named Jenny", exception1.getMessage());
    assertEquals("Choose a name for your plant", exception2.getMessage());
    assertEquals("Choose a name for your plant", exception3.getMessage());
    assertEquals("Choose your plants growing phase", exception4.getMessage());
  }

  /**
   * Tests the getPlantsByPredicate method of PlantOverview.
   */
  @Test
  public void testGetPlantsByPredicate() {
    Plant plant3 = new Plant("Julianne", "Young plant",5);
    plantOverview.addPlant(plant);
    plantOverview.addPlant(plant2);
    plantOverview.addPlant(plant3);

    Predicate<Plant> predicate = plant -> plant.getPhase().equals("Young plant");
    List<Plant> seedPlants = plantOverview.getPlantsByPredicate(predicate);
    assertTrue(seedPlants.contains(plant3));
    assertFalse(seedPlants.contains(plant));
    assertFalse(seedPlants.contains(plant2));
  }

  /**
   * Tests the getPlantsByName method of PlantOverview.
   */
  @Test
  public void testGetPlantByName() {
    plantOverview.addPlant(plant);
    plantOverview.addPlant(plant2);
    

    Plant retrievedPlant = plantOverview.getPlantByName("Jenny");
    assertEquals(plant, retrievedPlant);

    Plant nonExistentPlant = plantOverview.getPlantByName("Non-existent Plant");
    assertNull(nonExistentPlant);
    }
  /**
   * Tests the editPlant method of PlantOverview.
   */
  @Test
  public void testEditPlant() {
    plantOverview.addPlant(plant);
    plantOverview.addPlant(plant2);

    String result = plantOverview.editPlant("Jenny", null, null);
    assertEquals("Error: No changes selected", result);
    
    String result2 = plantOverview.editPlant("Jenny", "Seed", "4");
    assertEquals("Error: Selected phase matched old phase.\n" + "Success: Water interval has been updated.", result2);
    assertEquals(4, plant.getWaterInterval());

    String result3 = plantOverview.editPlant("Julie", "Young plant", null);
    assertEquals("Success: Phase has been updated.\n", result3);
    assertEquals("Young plant", plant2.getPhase());

    String result4 = plantOverview.editPlant("Julie", "Seed", "5");
    assertEquals("Error: You cannot downgrade the plant's phase.\n" + "Error: Selected water interval matched old water interval.", result4);

    String result5 = plantOverview.editPlant("Jenny", "Grown plant", "2");
    assertEquals("Success: Phase has been updated.\n"  + "Success: Water interval has been updated.", result5);
    assertEquals("Grown plant", plant.getPhase());
    assertEquals(2, plant.getWaterInterval());

    String result6 = plantOverview.editPlant("Jenny", "Seed", "2");
    assertEquals("Error: You cannot downgrade the plant's phase.\n" + "Error: Selected water interval matched old water interval.", result6);

    String result7 = plantOverview.editPlant("Jenny", "Young plant", null);
    assertEquals("Error: You cannot downgrade the plant's phase.\n", result7);
  }

  /**
   * Tests the filterPlantOverview method of PlantOverview.
   */
  @Test 
  public void testFilterPlantOverview() {
    Plant plant3 = new Plant("Johanne", "Grown plant", 4, Plant.fromStringToDate("2023-10-30 11:02:19"));
    Plant plant4 = new Plant("Josefine", "Seed", 3);
    plantOverview.addPlant(plant3);
    plantOverview.addPlant(plant4);

    PlantOverview result = PlantOverview.filterPlantOverview(null, plantOverview);
    assertEquals(plantOverview.getPlantOverview(), result.getPlantOverview());

    PlantOverview result2 = PlantOverview.filterPlantOverview("Seed", plantOverview);
    assertTrue(result2.getPlantOverview().stream().allMatch(p -> p.getPhase().equals("Seed")));
    assertEquals(plant4,result2.getPlantByName("Josefine"));

    PlantOverview result3 = PlantOverview.filterPlantOverview("Needs water", plantOverview);
    assertTrue(result3.getPlantOverview().stream().allMatch(Plant::needsWater));

    PlantOverview result4 = PlantOverview.filterPlantOverview("Grown plant", plantOverview);
    assertEquals(plant3,result4.getPlantByName("Johanne"));
    assertTrue(result4.getPlantOverview().stream().allMatch(p -> p.getPhase().equals("Grown plant")));
    
    PlantOverview result5 = PlantOverview.filterPlantOverview("Young plant", plantOverview);
    assertTrue(result5.getPlantOverview().isEmpty());

    PlantOverview result6 = PlantOverview.filterPlantOverview("Is hydrated", plantOverview);
    assertTrue(result6.getPlantOverview().stream().allMatch(p -> !p.needsWater()));

    PlantOverview result7 = PlantOverview.filterPlantOverview("No filter", plantOverview);
    assertEquals(plantOverview.getPlantOverview(), result7.getPlantOverview());
  }
  
  /**
   * Tests the status method of PlantOverview.
   */
  @Test
  public void testStatus() {
    assertEquals("Welcome to your Greenhouse!", plantOverview.status());
    plantOverview.addPlant(plant);
    plantOverview.addPlant(plant2);
    Plant plant3 = new Plant("Julianne", "Seed", 2, Plant.fromStringToDate("2023-10-28 11:02:19"));
    Plant plant4 = new Plant("Jorunn", "Seed", 6, Plant.fromStringToDate("2023-10-26 10:02:20"));
    Plant plant5 = new Plant("Janne", "Young plant", 3, Plant.fromStringToDate("2023-09-30 10:22:20"));
    assertEquals("All your plants are happy today<33", plantOverview.status());
    
    plantOverview.addPlant(plant3);
    assertEquals("Julianne need(s) water.", plantOverview.status());

    plantOverview.addPlant(plant4);
    assertEquals("Julianne and Jorunn need(s) water.", plantOverview.status());

    plantOverview.addPlant(plant5);
    assertEquals("Julianne, Jorunn and Janne need(s) water.", plantOverview.status());
  }
}
