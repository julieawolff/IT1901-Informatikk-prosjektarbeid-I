package greenhouse.springboot.restserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import core.Plant;
import core.PlantOverview;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Test class for testing the functionality of the PlantOverviewController class.
 */
@SpringBootTest(classes = PlantOverviewApplication.class)
public class PlantOverviewControllerTest {

  private Plant testPlant;
  private PlantOverview mockPlantOverview = new PlantOverview();
  
  @Mock
  private PlantOverviewService plantOverviewService;

  @InjectMocks
  private PlantOverviewController plantOverviewController;

  /**
   * Sets up the test environment by creating a test plant and adding it to the mock plant overview.
   */
  @BeforeEach
  public void setUp() {
    testPlant = new Plant("TestPlant", "Seed", 3);
    mockPlantOverview.addPlant(testPlant);
  }

  /**
   * Test for the getPlant()-method.
   */
  @Test
  public void testGetPlant() {
    when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
    String plantName = "TestPlant";
    Plant result = plantOverviewController.getPlant(plantName);

    verify(plantOverviewService).getPlantOverview();
    assertNotNull(result);
    assertEquals(testPlant, result);
  }

  /**
   * Test for the getPlant()-method when the plant does not exist.
   */
  @Test
  public void testGetPlant_notFound() {
    when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
    String plantName = "NonExistingPlant";
    Plant result = plantOverviewController.getPlant(plantName);

    verify(plantOverviewService).getPlantOverview();
    assertNull(result);
  }

  /**
   * Test for the postPlant()-method.
   */
  @Test
  public void testPostPlantOverview() {
    when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
    String name = "TestPlant2";
    String phase = "Young plant";
    int waterInterval = 5;

    Plant result = plantOverviewController.postPlant(name, phase, waterInterval);

    verify(plantOverviewService).getPlantOverview();
    verify(plantOverviewService).autoSavePlantOverview(mockPlantOverview);
    
    assertNotNull(result);
    assertEquals(name, result.getName());
    assertEquals(phase, result.getPhase());
    assertEquals(waterInterval, result.getWaterInterval());
  }


  /**
   * Test for the postPlant()-method when not adding a water interval.
   */
  @Test
    public void testPostPlant_withoutWaterInterval() {
    when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
    String name = "TestPlant2";
    String phase = "Seed";

    try {
      Plant result = plantOverviewController.postPlant(name, phase, 0);
      fail("Expected IllegalArgumentException but got result: " + result);
    } catch (IllegalArgumentException e) {
      verify(plantOverviewService, times(0)).getPlantOverview();
      verify(plantOverviewService, times(0)).autoSavePlantOverview(any(PlantOverview.class));
      assertEquals("Water interval cannot be 0", e.getMessage());
    }
  }


  /**
   * Test for the postPlant()-method when not entering the plant's name.
   */
  @Test
  public void testPostPlant_invalidInput() {
      when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
      assertThrows(IllegalArgumentException.class, () -> {
          plantOverviewController.postPlant("", "Seed", 3);
      });
  }


  /**
   * Test for the putPlant()-method.
   */
  // @Test
  // public void testPutPlant() {
  //   when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
  //   String name = "TestPlant";
  //   String phase = "Young plant";
  //   String waterInterval = "empty";
  //   String water = "True";

  //   plantOverviewController.putPlant(name, phase, waterInterval, water);
  //   verify(plantOverviewService).getPlantOverview();
  //   verify(plantOverviewService).autoSavePlantOverview(mockPlantOverview);

  //   //assertEquals(phase, plantOverviewController.getPlant(name).getPhase());
  //   //assertEquals(3, plantOverviewController.getPlantOverview().getPlantByName(name).getWaterInterval());

  //   if (water.equals("True")) {
  //     assertNotNull(plantOverviewController.getPlant(name).getLastWatered());
  //   } else {
  //     assertNull(plantOverviewController.getPlant(name).getLastWatered());
  //   }
  // }

  /**
   * Test for the putPlant()-method when only changing the plant's phase.
   */
  // @Test
  // public void testPutPlantWithPhase() {
  //   when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);
  //   String name = "TestPlant";
  //   String phase = "Grown plant";
  //   String waterInterval = "empty";
  //   String water = "empty";

  //   plantOverviewController.putPlant(name, phase, waterInterval, water);

  //  assertEquals("Grown plant", plantOverviewController.getPlant(name).getPhase());
  // }


  /**
   * Test for the deletePlant()-method.
   */
  @Test
  public void testDeletePlant() {
    when(plantOverviewService.getPlantOverview()).thenReturn(mockPlantOverview);

    String nameToDelete = "TestPlant";
    boolean result = plantOverviewController.deletePlant(nameToDelete);

    verify(plantOverviewService).getPlantOverview();
    verify(plantOverviewService).autoSavePlantOverview(mockPlantOverview);

    assertTrue(result);
    assertNull(mockPlantOverview.getPlantByName(nameToDelete));
  }
  
}



