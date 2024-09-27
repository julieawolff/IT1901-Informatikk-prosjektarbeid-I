package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.NodeQuery;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class contains JUnit tests for the NewPlantController class, which is responsible for
 * handling user interactions related to adding new plants in the graphical user interface of the application.
 * Uses JavaFX TestFX library for UI testing.
 */
public class NewPlantControllerTest extends ApplicationTest {
  private AppController controller;

   /**
    * Overrides the start method to initialize the JavaFX application with the specified FXML file.
    *
    * @param stage The primary stage for the JavaFX application.
    * @throws IOException If an error occurs while loading the FXML file.
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
   * Tests the initial state of the plant table and ensures that it is empty before
   * adding any plants. 
   */
  @Test
  public void testInitialState() {
    assertEquals("true",controller.getIsTest());
    clickOn("#plantTable");
    ListView<String> plantTable = lookup("#plantTable").query();
    List<String> table = plantTable.getItems();
    assertTrue(table.isEmpty());
  }

  /**
   * Tests the process of adding a new plant with a nickname, growth phase, and water interval.
   * Verifies the presence of the new plant in the plant table.
   */
  @Test
  public void testNewPlantButtonPushed() {
    clickAddPlant("Thomas", "Young plant", "3");
    ListView<String> plantTable = lookup("#plantTable").query();
    List<String> table = plantTable.getItems();
    assertEquals("Thomas", table.get(0));
    FrontPageControllerTest.clearJsonFile();
  }

  /**
   * Tests the scenario of attempting to add a new plant without specifying the water interval
   * and verifies the displayed alert message.
   */
  @Test
  public void testNewPlantWithoutWaterInterval() {
    clickAddPlant("Tom", "Grown plant", null);
    assertAlertMessage("Choose how often your plant needs water");
  }

  /**
   * Tests the scenario of attempting to add a new plant without specifying the name
   * and verifies the displayed dialog message.
   */
  @Test
  public void testNewPlantWithoutName() {
    clickAddPlant(null, "Seed", "6");
    assertAlertMessage("Choose a name for your plant");
  }

  /**
   * Tests the scenario of attempting to add a new plant without specifying the growth phase
   * and verifies the displayed dialog message.
   */
  @Test
  public void testNewPlantWithoutPhase() {
    clickAddPlant("Timmy", null, "20");
    assertAlertMessage("Choose your plants growing phase");
  }

  /**
   * Performs the actions of clicking the "Add Plant" button, entering the provided details.
   *
   * @param nickname      The nickname of the new plant.
   * @param phase         The growth phase of the new plant.
   * @param numberOfDays  The water interval of the new plant.
   */
  private void clickAddPlant(String nickname, String phase, String numberOfDays) {
    clickOn("#addPlant");
    if (nickname != null) clickOn("#nickname").write(nickname);
    if (phase != null) clickOn("#phaseOptions").clickOn(phase);
    if (numberOfDays != null) clickOn("#numberOfDays").clickOn(numberOfDays);
    clickOn("#addPlant");
  }

  /**
   * Asserts that an alert with the expected message is displayed.
   *
   * @param expectedMessage The expected message in the alert.
   */
  private void assertAlertMessage(String expectedMessage) {
    Node dialogPane = lookup(".dialog-pane").query();
    assertNotNull(dialogPane);
    NodeQuery n = from(dialogPane).lookup((Text t) -> t.getText().startsWith(expectedMessage));
    assertTrue(n.queryText().toString().contains(expectedMessage));
    clickOn("OK");
  }
}
