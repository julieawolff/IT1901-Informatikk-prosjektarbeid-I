package greenhouse.springboot.restserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import core.Plant;
import core.PlantOverview;
import json.PlantPersistence;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing the functionality of the PlantOverviewService class.
 */
public class PlantOverviewServiceTest {

    private PlantOverview testPlantOverview;

    @Mock
    private PlantPersistence plantPersistence;

    @InjectMocks
    private PlantOverviewService plantOverviewService;

    /**
     * Sets up the test environment by creating a new instance of PlantOverviewService and initializing
     * a test plant overview with sample plants.
     */
    @BeforeEach
    public void setUp() {
        plantOverviewService = new PlantOverviewService();
        testPlantOverview = new PlantOverview();
        testPlantOverview.addPlant(new Plant("TestPlant1", "Seed", 1));
        testPlantOverview.addPlant(new Plant("TestPlant2", "Young plant", 2));
    }


    /**
     * Test for the getPlantOverview() method,
     * ensuring that the returned plant overview is not null.
     */
    @Test
    public void testGetPlantOverview() {
        assertNotNull(plantOverviewService.getPlantOverview());
    }

  }