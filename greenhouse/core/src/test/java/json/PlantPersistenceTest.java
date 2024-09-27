package json;

import core.Plant;
import core.PlantOverview;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * The `PlantPersistenceTest` class contains JUnit test methods for testing the serialization and deserialization
 * of `PlantOverview` objects using the `PlantPersistence` class. It focuses on testing the functionality of
 * serializing and deserializing `PlantOverview` objects using a `StringWriter` and `StringReader`.
 */
public class PlantPersistenceTest {
    
  private PlantPersistence persistence = new PlantPersistence();

  /**
   * Creates a sample `PlantOverview` object for testing purposes.
   *
   * @return A `PlantOverview` object with sample `Plant` objects added to it.
   */
  private PlantOverview createModelPlantOverview() {
    Date date1 = Plant.fromStringToDate("2023-01-01 00:00:00");
    Date date2 = Plant.fromStringToDate("2023-01-02 00:00:00");
    Plant p = new Plant("Jacob","Young plant",2, date1);
    Plant p2 = new Plant("Thomas","Seed",3, date1);
    p.setLastWatered(date2);
    p2.setLastWatered(date2);

    PlantOverview o = new PlantOverview();
    o.addPlant(p);
    o.addPlant(p2);

    return o;
  }

  /**
   * Utility method to check the correctness of a `Plant` object's attributes.
   *
   * @param p The Plant object to be checked.
   * @param name The expected name of the Plant.
   * @param phase The expected growth phase of the Plant.
   * @param waterInt The expected water interval of the Plant.
   * @param creationDate  The expected creation date of the plant.
   * @param lastWatered   The expected last watered date of the plant.
   */
  protected static void checkPlant(Plant p, String name, String phase, int waterInt, Date creationDate, Date lastWatered) {
    assertEquals(p.getName(),name);
    assertEquals(p.getPhase(),phase);
    assertEquals(p.getWaterInterval(), waterInt);
    assertEquals(p.getCreationDate(), creationDate);
    assertEquals(p.getLastWatered(), lastWatered);
  }

  /**
   * Utility method to check the correctness of two `Plant` objects by comparing their attributes.
   *
   * @param p The first Plant object.
   * @param p2 The second Plant object.
   */
  private void checkPlant(Plant p, Plant p2) {
    checkPlant(p, p2.getName(), p2.getPhase(), p2.getWaterInterval(), p2.getCreationDate(), p2.getLastWatered());
  }

  /**
   * Checks the correctness of two `PlantOverview` objects by comparing their content.
   *
   * @param overview The first `PlantOverview` object.
   * @param overview2 The second `PlantOverview` object to be compared against the first.
   */
  private void checkSamplePlantOverview(PlantOverview overview, PlantOverview overview2) {
    Iterator<Plant> plantIterator = overview.getPlantOverview().iterator();
    Iterator<Plant> plantIterator2 = overview2.getPlantOverview().iterator();
    
    assertTrue(plantIterator2.hasNext());
    assertTrue(plantIterator.hasNext());
    checkPlant(plantIterator.next(), plantIterator2.next());
    
    assertTrue(plantIterator2.hasNext());
    assertTrue(plantIterator.hasNext());
    checkPlant(plantIterator.next(), plantIterator2.next());

    assertFalse(plantIterator2.hasNext());
    assertFalse(plantIterator.hasNext());
  }

  /**
   * Test the serialization and deserialization of `PlantOverview` objects using a `StringWriter` and `StringReader`.
   */
  @Test
  public void testSerializersDeserializersUsingStringWriter() {
    PlantOverview overview = new PlantOverview();
    overview = createModelPlantOverview();
    try {
      StringWriter writer = new StringWriter();
      persistence.writePlantOverview(writer,overview);
      String json = writer.toString();

      PlantOverview overview2 = persistence.readPlantOverview(new StringReader(json));
      checkSamplePlantOverview(overview, overview2);
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Test case for the scenario where the provided reader is not ready, simulating a situation
   * where there is no data available to be read. The test checks whether the readPlantOverview
   * method handles the unready reader by returning a new PlantOverview object and ensuring
   * that the resulting PlantOverview is considered empty.
   */
  @Test
  public void testUnReadyReader() {
    try {
      PlantOverview result = persistence.readPlantOverview(Reader.nullReader());
      assertNotNull(result);
      assertTrue(result.getPlantOverview().isEmpty());
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * Tests the save functionality in the PlantPersistence class when the save file path is not set.
   * 
   * <p>Verifies that an IllegalStateException is thrown when attempting to save a
   * PlantOverview without setting the save file path.</p>
   */
  @Test
  public void testSavePlantOverviewNoFilePath() {
    PlantOverview overview = createModelPlantOverview();
    assertThrows(IllegalStateException.class,
        () -> new PlantPersistence().savePlantOverview(overview));
  }

  /**
   * Tests the creation of the Jackson module in the PlantPersistence class.
   * 
   * <p>Verifies that the createJacksonModule method returns a non-null instance
   * of SimpleModule.</p>
   */
  @Test
  public void testCreateJacksonModule() {
    assertNotNull(PlantPersistence.createJacksonModule().getClass());
  }
}
