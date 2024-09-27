package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.NodeQuery;

import core.Plant;
import core.PlantOverview;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Test class for the PopUpController, which is responsible for handling various 
 * pop-up dialogs in the graphical user interface of the application.
 * Uses JavaFX TestFX library for UI testing.
 */
public class PopUpControllerTest extends ApplicationTest {
  private PlantOverview overview;
  private AppController controller;

  /**
   * Overrides the start method to initialize the JavaFX application with the specified FXML file.
   *
   * @param stage The primary stage for the application.
   * @throws IOException If an I/O error occurs while loading the FXML file.
   */
  @Override
  public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("App_test.fxml"));
      Parent root = fxmlLoader.load();
      controller = fxmlLoader.getController();
      stage.setScene(new Scene(root));
      stage.show();
  }

  /**
   * Initializes a PlantOverview and adds a plant for testing purposes.
   * Invoked before each test method.
   */
  @BeforeEach
  public void setUp() {
    overview = new PlantOverview();
    Date creationDate = Plant.fromStringToDate("2023-02-11 00:00:00");
    Plant p1 = new Plant("Johannes","Seed", 50, creationDate);
    overview.addPlant(p1);
    controller.writeToFile(overview);
    controller.showOverview();
  }

  /**
   * Tests the initialization of the application and verifies the content of the plant details pop-up.
   * Clicks on a plant in the table, triggers a pop-up, and asserts the expected content.
   */
  @Test
  public void testInitialization() {
    clickOn("#plantTable").clickOn("Johannes NEEDS WATER");
    Label plant = lookup("#plantNameLabel").query();
    assertEquals("Johannes", plant.getText());

    TextArea plantDescription = lookup("#plantDescription").query();
    assertEquals(overview.getPlantByName("Johannes").toString(), plantDescription.getText());
  }

  /**
   * Tests various scenarios of editing plant details; including phase and water interval changes.
   * Editing both properties at the same time.
   */
  @Test
  public void testEditPlant() {
    // Edit plant with success
    performPlantEdit("Johannes NEEDS WATER", "Young plant", "10",
      "Success: Phase has been updated.\nSuccess: Water interval has been updated.");

    // Attempt to downgrade phase and change to the same water interval
    performPlantEdit("Johannes NEEDS WATER", "Seed", "10",
      "Error: You cannot downgrade the plant's phase.\nError: Selected water interval matched old water interval.");

    // Attempt to match old phase and change water interval 
    performPlantEdit("Johannes NEEDS WATER", "Young plant", "2",
      "Error: Selected phase matched old phase.\nSuccess: Water interval has been updated.");

    // Attempt to downgrade phase and change water interval
    performPlantEdit("Johannes NEEDS WATER", "Seed", "20",
        "Error: You cannot downgrade the plant's phase.\nSuccess: Water interval has been updated.");

    // Attempt to change phase and match old water interval 
    performPlantEdit("Johannes NEEDS WATER", "Grown plant", "20",
        "Success: Phase has been updated.\nError: Selected water interval matched old water interval.");

    // Attempt to match old phase and old water interval
    performPlantEdit("Johannes NEEDS WATER", "Grown plant", "20",
        "Error: Selected phase matched old phase.\nError: Selected water interval matched old water interval.");

    FrontPageControllerTest.clearJsonFile();
  }

  /**
   * Tests additional scenarios of editing plant details; including phase and water interval changes.
   * Editing only one of the property at once.
   */
  @Test
  public void testEditPlant2() {
    performPhaseEdit("Grown plant", "Success: Phase has been updated.");

    performPhaseEdit("Seed", "Error: You cannot downgrade the plant's phase.");

    performPhaseEdit("Young plant", "Error: You cannot downgrade the plant's phase.");


    performWaterIntervalEdit("2", "Success: Water interval has been updated.");

    performWaterIntervalEdit("2", "Error: Selected water interval matched old water interval.");
}

  /**
   * Tests the deletion of a plant and verifies the alert message indicating successful deletion.
   */
  @Test
  public void testDeletePlant() {
    performActionAndVerifyAlert("Johannes NEEDS WATER", "#deletePlant", "Successfully deleted the plant", true);
    assertPlantTableIsEmpty("#plantTable");
  }

  /**
   * Tests the watering of a plant.
   */
  @Test 
  public void testWaterPlant() {
    performActionAndVerifyAlert("Johannes NEEDS WATER", "#waterPlant", "Johannes has been watered:)", true);
    
    ListView<String> plantTable = lookup("#plantTable").query();
    List<String> table = plantTable.getItems();
    assertEquals("Johannes", table.get(0));

    performActionAndVerifyAlert("Johannes", "#waterPlant",
            "Johannes does not need water. \nDo you still wanna water the plant?", false);
    clickOn("No");
    
    performActionAndVerifyAlert("Johannes", "#waterPlant",
            "Johannes does not need water. \nDo you still wanna water the plant?", false);
    clickOn("Yes");

    assertExpectedTextAlert("Johannes has been watered, but did not need it.\nTry watering this plant less frequently.");
  }

  /**
   * Clears the JSON file used for testing after each test.
   */
  @AfterEach
  public void clearFile() {
    FrontPageControllerTest.clearJsonFile();
  }

  /**
   * Tries to change the phase for a given plant and verifies the expected alert message.
   * Utilizes the performPlantEdit2 method for code reuse.
   *
   * @param phase The new phase to set for the plant.
   * @param expectedMessage The expected message in the alert.
   */
  private void performPhaseEdit(String phase, String expectedMessage) {
      performPlantEdit2("Johannes NEEDS WATER", "#phaseOptions", phase, expectedMessage);
  }

  /**
   * Tries to change the waterinterval for a given plant and verifies the expected alert message.
   * Utilizes the performPlantEdit2 method for code reuse.
   *
   * @param interval The new water interval to set for the plant.
   * @param expectedMessage The expected message in the alert.
   */
  private void performWaterIntervalEdit(String interval, String expectedMessage) {
      performPlantEdit2("Johannes NEEDS WATER", "#numberOfDays", interval, expectedMessage);
  }

  /**
   * Tries to edit a plant and verifies the expected alert message.
   *
   * @param plantName The name of the plant to edit.
   * @param phase The new phase to set for the plant.
   * @param numberOfDays The new water interval to set for the plant.
   * @param expectedMessage The expected message in the alert.
   */
  private void performPlantEdit(String plantName, String phase, String numberOfDays, String expectedMessage) {
    clickOn("#plantTable").clickOn(plantName);
    clickOn("#phaseOptions").clickOn(phase);
    clickOn("#numberOfDays").clickOn(numberOfDays);
    clickOn("#editPlant");

    assertExpectedTextAlert(expectedMessage);
    
    clickOn("OK");
  } 

  /**
   * Tries to edit a plant and verifies the expected alert message.
   *
   * @param plantName The name of the plant to edit.
   * @param optionId Which choicebox to be clicked on. 
   * @param optionText The new water interval or phase.
   * @param expectedMessage The expected message in the alert.
   */
  private void performPlantEdit2(String plantName, String optionId, String optionText, String expectedMessage) {
    clickOn("#plantTable").clickOn(plantName);
    clickOn(optionId).clickOn(optionText);
    clickOn("#editPlant");

    assertExpectedTextAlert(expectedMessage);

    clickOn("OK");
  }

  /**
   * Clicks on a plant in the table, triggers the specified action, and asserts the expected alert message.
   *
   * @param itemName The name of the plant to perform the action on.
   * @param actionButtonId The ID of the button representing the action.
   * @param expectedText The expected text in the alert.
   * @param clickOkAfterAction Whether to click the "OK" button in the alert after performing the action.
   */
  private void performActionAndVerifyAlert( String itemName, String actionButtonId, String expectedText, boolean clickOkAfterAction) {
    clickOn("#plantTable").clickOn(itemName);
    clickOn(actionButtonId);

    assertExpectedTextAlert(expectedText);
    
    if(clickOkAfterAction) {
      clickOn("OK");
    }
  }

  /**
   * Asserts that the plant table with the specified ID is empty.
   *
   * @param tableId The ID of the plant table to check for emptiness.
   */
  private void assertPlantTableIsEmpty(String tableId) {
    clickOn(tableId);
    ListView<String> plantTable = lookup(tableId).query();
    assertTrue(plantTable.getItems().isEmpty());
  }

  /**
   * Verifies that the alert displayed in the UI contains the specified expected text.
   *
   * @param expectedText The text expected to be present in the alert.
   */
  private void assertExpectedTextAlert(String expectedText) {
    Node dialogPane = lookup(".dialog-pane").query();
    assertNotNull(dialogPane);
    
    NodeQuery n = from(dialogPane).lookup((Text t) -> t.getText().startsWith(expectedText));
    assertTrue(n.queryText().toString().contains(expectedText));
  }
}
