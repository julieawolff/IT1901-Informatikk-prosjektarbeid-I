package ui;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The controller class for handling the addition of new plants in the remote plant management system.
 * This class manages the user interface components and interacts with the data model.
 */
public class RemoteNewPlantController {
    
  @FXML private TextField nickname;

  @FXML private ChoiceBox<String> phaseOptions;
  
  @FXML private ChoiceBox<String> numberOfDays;

  @FXML private Button addPlant;

  @FXML private ImageView tallPlant1;
  @FXML private ImageView tallPlant2;

  private FXMLLoader loader;
  //private RemoteAppController remoteAppController = new RemoteAppController();
  private RemotePlantOverviewAccess remotePlantOverviewAccess;

   /**
   * Initializes the controller, setting up default values and event handlers.
   */
  @FXML
  public void initialize() {
    initializeCheckboxes(phaseOptions, numberOfDays);
  }

  /**
   * Initializes the provided choice boxes with default options.
   *
   * @param phaseOptions The choice box for selecting the growth phase.
   * @param numberOfDays The choice box for selecting the number of days between watering.
   */
  public static void initializeCheckboxes(ChoiceBox<String> phaseOptions,
        ChoiceBox<String> numberOfDays) {
    ObservableList<String> phaseOptionsList = FXCollections.observableArrayList(
        "Seed", "Young plant", "Grown plant");
    ObservableList<String> numberOfDaysList = FXCollections.observableArrayList(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20", "30", "50", "100");

    phaseOptions.getItems().addAll(phaseOptionsList);
    numberOfDays.getItems().addAll(numberOfDaysList);
  }

  /**
   * Handles the event when the "Add Plant" button is pushed.
   *
   * @param event The ActionEvent triggered by the button push.
   * @throws IOException If an error occurs during the addition of the new plant.
   */
  public void addPlantButtonPushed(ActionEvent event) throws IOException {
    String nicknameString = nickname.getText();
    String phaseString = phaseOptions.getValue();
    String waterInterval = numberOfDays.getValue();
    int numWaterInterval = 0;

    try {
      numWaterInterval = Integer.parseInt(waterInterval);
    } catch (NumberFormatException e) {
      Alert a1 = new Alert(AlertType.INFORMATION, "Choose how often your plant needs water");
      a1.show();
      return;
    }
    try {
      remotePlantOverviewAccess.newPlant(nicknameString, phaseString, Integer.toString(numWaterInterval));
    } catch (IllegalArgumentException e) {
      Alert a2 = new Alert(AlertType.INFORMATION, e.getMessage());
      a2.show();
      return;
    }
    returnToApp(event);
  }

  /**
   * Returns to the main application view.
   *
   * @param event The ActionEvent triggered by the return action.
   * @throws IOException If an error occurs during the navigation back to the main application.
   */
  public void returnToApp(ActionEvent event) throws IOException {
    // if (remoteAppController.getIsTest() == null) {
    //   loader = new FXMLLoader(getClass().getResource("App.fxml"));
    // } else {
    //   loader = new FXMLLoader(getClass().getResource("App_Test.fxml"));
    // }
    loader = new FXMLLoader(getClass().getResource("RemoteApp.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    primaryStage.setScene(scene);
  }

  /**
   * Sets the reference to the RemoteAppController.
   *
   * @param remoteAppController The RemoteAppController instance to be set.
   */
  // protected void setAppController(RemoteAppController remoteAppController) {
  //   this.remoteAppController = remoteAppController;
  // }
  
  /**
   * Sets the reference to the RemotePlantOverviewAccess.
   *
   * @param remotePlantOverviewAccess The RemotePlantOverviewAccess instance to be set.
   */
  protected void setRemotePlantOverviewAccess(RemotePlantOverviewAccess remotePlantOverviewAccess) {
    this.remotePlantOverviewAccess = remotePlantOverviewAccess;
  } 
}
