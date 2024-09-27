package ui;

import core.Plant;
import core.PlantOverview;
import java.io.IOException;
import java.util.Date;
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
 * The `NewPlantController` class manages the addition of
 * new plants to the application and interacts with the user interface.
 */
public class NewPlantController {
    
  @FXML private TextField nickname;

  @FXML private ChoiceBox<String> phaseOptions;
  
  @FXML private ChoiceBox<String> numberOfDays;

  @FXML private Button addPlant;

  @FXML private ImageView tallPlant1;
  @FXML private ImageView tallPlant2;

  private PlantOverview plantOverview;
  private FXMLLoader loader;
  private AppController appController;

  /**
   * Initializes the controller after its root element has been completely processed.
   * It calls the initializeCheckboxes(ChoiceBox, ChoiceBox) method to set up
   * the choices for plant phases and watering intervals.
   */
  @FXML
  void initialize() {
    initializeCheckboxes(phaseOptions, numberOfDays);
  }

  /**
   * Initializes the provided ChoiceBoxes with predefined options for plant phases and
   * watering intervals.
   *
   * @param phaseOptions The ChoiceBox for selecting plant phases.
   * @param numberOfDays The ChoiceBox for selecting watering intervals in days.
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
   * Handles the button click event when the "Add Plant" button is pushed. Retrieves
   * necessary information (nickname, plant phase, watering interval) and adds a new
   * plant to the plant overview. Displays alerts for invalid input or exceptions.
   *
   * @param event The ActionEvent triggered by the "Add Plant" button.
   * @throws IOException If an I/O error occurs.
   */
  public void addPlantButtonPushed(ActionEvent event) throws IOException {
    
    plantOverview = appController.getPlantOverview();

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
      plantOverview.addPlant(new Plant(nicknameString, phaseString, numWaterInterval, new Date()));
    } catch (IllegalArgumentException e) {
      Alert a2 = new Alert(AlertType.INFORMATION, e.getMessage());
      a2.show();
      return;
    }
    appController.writeToFile(plantOverview);
    returnToApp(event);
  }

  /**
  * Returns to the main application screen by loading the appropriate FXML file.
  * This method is responsible for navigating back to the main application screen.
  * It checks whether the application is in test mode or regular mode and loads the
  * corresponding FXML file. The method then sets the loaded scene in the primary
  * stage, effectively transitioning the user interface back to the main application.
  *
  * @param event The ActionEvent triggered when the "Return" button is pushed.
  *
  * @throws IOException if there is an I/O error while loading the FXMLLoader.
  */
  public void returnToApp(ActionEvent event) throws IOException {
    if (appController.getIsTest() == null) {
      loader = new FXMLLoader(getClass().getResource("App.fxml"));
    } else {
      loader = new FXMLLoader(getClass().getResource("App_Test.fxml"));
    }
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    primaryStage.setScene(scene);
  }

  /**
   * Sets the AppController for this class.
   *
   * @param controller The AppController to be set.
   */
  protected void setAppController(AppController controller) {
    appController = controller;
  }
}
