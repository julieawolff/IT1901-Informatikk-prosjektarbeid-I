package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testfx.framework.junit5.ApplicationTest;

import core.Plant;
import core.PlantOverview;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Test class for the AppController.
 * Uses JavaFX TestFX library for UI testing.
 */
public class RemoteAppControllerTest extends ApplicationTest{
  
  private AppController controller;
  
  /**
   * Overrides the start method to initialize the JavaFX application with the specified FXML file.
   *
   * @param stage The primary stage for the application.
   * @throws IOException If an I/O error occurs while loading the FXML file.
   */
  //@Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RemoteApp.fxml"));
    Parent root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  /**
   * Test method to verify the behavior of filtering the plant overview with different options.
   * Creates a PlantOverview with predefined plants, writes it to a test file, and tests the filtering options.
   */
  //@Test
  void testShowFilteredOverview() {
    Plant plant1 = new Plant("Marie", "Grown plant", 2, Plant.fromStringToDate("2023-10-28 11:02:19"));
    Plant plant2 = new Plant("Marianne", "Seed", 6, Plant.fromStringToDate("2023-10-26 10:02:20"));
    Plant plant3 = new Plant("Margrethe", "Young plant", 3, Plant.fromStringToDate("2023-09-30 10:22:20"));
    Plant plant4 = new Plant("Mona", "Seed", 7);
    Plant plant5 = new Plant("Mille", "Seed", 5);
    PlantOverview overview = new PlantOverview(Arrays.asList(plant1, plant2, plant3, plant4, plant5));
    controller.writeToFile(overview);
    controller.showOverview();

    // Test with different filter options
    testFilterOption("Seed", 3, "Marianne NEEDS WATER", "Mona", "Mille");
    testFilterOption("Grown plant", 1, "Marie NEEDS WATER");
    testFilterOption("Young plant", 1, "Margrethe NEEDS WATER");
    testFilterOption("Needs water", 3, "Marie NEEDS WATER", "Marianne NEEDS WATER", "Margrethe NEEDS WATER");
    testFilterOption("Is hydrated", 2, "Mona", "Mille");
    //FrontPageControllerTest.clearJsonFile();
  }

  /**
   * Helper method to test filtering options and assert the expected count and items in the plant table.
   *
   * @param filterOption The filter option to apply.
   * @param expectedCount The expected count of items after applying the filter.
   * @param expectedItems The expected items in the plant table after applying the filter.
   */
  private void testFilterOption(String filterOption, int expectedCount, String... expectedItems) {
    clickOn("#filterOptions").clickOn(filterOption);
    clickOn("#filterButton");
    ListView<String> plantTable = lookup("#plantTable").query();
    List<String> table = plantTable.getItems();

    assertEquals(expectedCount, table.stream().filter(Arrays.asList(expectedItems)::contains).count());
  }
}
