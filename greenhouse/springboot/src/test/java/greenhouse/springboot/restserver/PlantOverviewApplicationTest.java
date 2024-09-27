package greenhouse.springboot.restserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import core.PlantOverview;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test class for testing the functionality of the PlantOverviewApplication class.
 */
@SpringBootTest(classes = PlantOverviewApplication.class)
public class PlantOverviewApplicationTest {

    @Autowired
    private PlantOverview plantOverview;

    @Autowired
    private ObjectMapper objectMapper;


    /**
     * Verifies that the application context loads successfully, and the necessary beans are initialized.
     */
    @Test
    public void contextLoads() {
        assertNotNull(plantOverview);
        assertNotNull(objectMapper);
    }

    /**
     * Verifies that the ObjectMapper bean is not null.
     */
    @Test
    public void objectMapperNotNull() {
        assertNotNull(objectMapper);
    }

    /**
     * Verifies that the PlantOverview bean is not null.
     */
    @Test
    public void plantOverviewNotNull() {
        assertNotNull(plantOverview);
    }
}


