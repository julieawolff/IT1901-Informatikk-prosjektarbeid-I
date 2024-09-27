package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.NodeQuery;

import core.Plant;
import core.PlantOverview;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.text.Text;
import json.PlantPersistence;

/**
 * Test class for the FrontPageController, which controls the front page of the Greenhouse application.
 * Uses JavaFX TestFX library for UI testing.
 */
public class FrontPageControllerTest extends ApplicationTest {
  private PlantPersistence plantPersistence = new PlantPersistence();
  
  /**
   * Overrides the start method to initialize the JavaFX application with the specified FXML file.
   *
   * @param stage The stage for the frontpage.
   * @throws IOException If an I/O error occurs while loading the FXML file.
   */
  @Override
  public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("FrontPage_test.fxml"));
      Parent root = fxmlLoader.load();
      stage.setScene(new Scene(root));
      stage.show();
  }

  /**
   * Test method to verify the behavior when the "Start" button is pushed without any plants.
   */
  @Test
  public void testStartButtonPushedWithoutPlants() {
    clickOn("#startButton");
    assertAlertText("Welcome to your Greenhouse!");
  }

  /**
   * Test method to verify the behavior when the "Start" button is pushed with a plant needing water.
   */
  @Test
  public void testStartButtonPushedWithPlants() {
    Date creationDate = Plant.fromStringToDate("1992-02-11 00:00:00");
    Plant p1 = new Plant("Tom Hanks", "Young plant", 20);
    Plant p2 = new Plant("Taylor Lautner", "Grown plant", 10, creationDate);
    PlantOverview overview = new PlantOverview(Arrays.asList(p1, p2));

    writePlantsToTestFile(overview);

    clickOn("#startButton");
    assertAlertText("Taylor Lautner need(s) water.");
    clearJsonFile();
  }

  /**
   * Test method to verify the behavior when the "Start" button is pushed with all plants being hydrated.
   */
  @Test
  public void testStartButtonPushedWithAllPlantsHappy() {
    Plant p1 = new Plant("Tom", "Young plant", 4);
    PlantOverview overview = new PlantOverview();
    overview.addPlant(p1);

    writePlantsToTestFile(overview);

    clickOn("#startButton");
    assertAlertText("All your plants are happy today<33");
    clearJsonFile();
  }

  /**
   * Helper method to assert the text displayed in the Alert.
   *
   * @param expectedText The expected text in the Alert.
   */
  private void assertAlertText(String expectedText) {
    Node dialogPane = lookup(".dialog-pane").query();
    assertNotNull(dialogPane);
    NodeQuery n = from(dialogPane).lookup((Text t) -> t.getText().startsWith(expectedText));
    assertTrue(n.queryText().toString().contains(expectedText));
  }

  /**
   * Helper method to write a PlantOverview to a test JSON file.
   *
   * @param overview The PlantOverview to write to the test file.
   */
  private void writePlantsToTestFile(PlantOverview overview) {
    String jsonFilePath = "src/test/resources/ui/test-app.json";
    try {
      plantPersistence.writePlantOverview(new FileWriter(jsonFilePath), overview);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } 
  }

  /**
   * Helper method to clear the content of the test JSON file.
   */
  public static void clearJsonFile(){
    String jsonFilePath = "src/test/resources/ui/test-app.json";

    try(FileWriter fileWriter = new FileWriter(jsonFilePath);) {
      // Write an empty JSON object to the file
      fileWriter.write("{}");
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Error while clearing the JSON file: " + e.getMessage());
    }
  }  
}
