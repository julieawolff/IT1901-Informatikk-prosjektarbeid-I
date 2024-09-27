package ui;

import core.PlantOverview;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for the front page of the application.
 */
public class FrontPageController {

  @FXML private Button start;
  @FXML private String isTest;

  private FXMLLoader loader;

  /**
   * Event handler for the "Start" button. Loads the appropriate FXML file.
   *
   * @param event The ActionEvent triggered by the "Start" button push.
   * @throws IOException If an error occurs while loading the FXML file.
   */
  public void startButtonPushed(ActionEvent event) throws IOException {
    if (getIsTest() == null) {
      loader = new FXMLLoader(getClass().getResource("App.fxml"));
    } else {
      loader = new FXMLLoader(getClass().getResource("App_Test.fxml"));
    }
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    primaryStage.setScene(scene);

    AppController appController = loader.getController();
    PlantOverview overview = appController.getPlantOverview();
    greenhouseStatus(overview);
  }

  /**
   * Retrieves the value of the `isTest` flag.
   *
   * @return The value of the `isTest` flag.
   */
  public String getIsTest() {
    return isTest;
  }

  /**
   * Displays the status of plants in the greenhouse.
   * This method retrieves the status of plants from the provided 'PlantOverview' object
   * and displays it to the user as an informational alert.
   *
   * @param overview The 'PlantOverview' object containing information about the plants.
   */
  private void greenhouseStatus(PlantOverview overview) {
    String statusOfPlants = overview.status();
    Alert a = new Alert(AlertType.INFORMATION, statusOfPlants);
    a.show();
  }
}