package ui;

import core.Plant;
import core.PlantOverview;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Controller class for the pop-up window.
 */
public class PopUpController {
  @FXML private ChoiceBox<String> phaseOptions;

  @FXML private ChoiceBox<String> numberOfDays;

  @FXML private TextArea plantDescription;

  @FXML private Label plantNameLabel;

  @FXML private Text plantMood;

  @FXML private Button waterPlant;

  private AppController appController;

  private Plant selectedPlant;

  private PlantOverview plantOverview;

  private Stage currentStage;


  /**
   * Initializes the PopUpController by setting up checkboxes for
   * the given phase options and number of days.
   */
  public void initialize() {
    NewPlantController.initializeCheckboxes(phaseOptions, numberOfDays);
  }

  /**
   * Initializes the scene by updating the plant name label, plant description,
   * and plant overview.
   * This method should be called to display the selected plant's information
   * in the user interface.
   */
  public void initializeScene() {
    plantNameLabel.setText(selectedPlant.getName());
    plantOverview = appController.getPlantOverview();
   
    selectedPlant = plantOverview.getPlantByName(plantNameLabel.getText());
   
    plantMood.setText(selectedPlant.needsWater() ? "Thirsty:(" : "Hydrated:)");
    plantDescription.setText(selectedPlant.toString());
    waterPlant.setText("Water " + selectedPlant.getName());
  }
  
  /**
   * Sets the selected plant to a new plant instance.
   * This method is used to update the selected plant for editing or other operations.
   *
   * @param selectedPlant The new Plant instance to be set as the selected plant.
   */
  public void setSelectedPlant(Plant selectedPlant) {
    this.selectedPlant = new Plant(selectedPlant); 
  }

  /**
   * Sets the current stage for the user interface.
   * This method is used to set the stage that the user interface is associated with.
   *
   * @param newStage The new Stage to set as the current stage.
   */
  protected void setStage(Stage newStage) {
    currentStage = newStage;
  }
  
  /**
   * Edits a plant's information, including its growth phase and water interval,
   * based on user input. Retrieves the selected growth phase and water interval
   * values from UI elements and calls the editPlant method in the PlantOverview
   * class to edit the plant's details. Displays an alert message to provide
   * feedback to the user based on the outcome of the edit operation.
   */
  public void editPlant() {
    String newPhaseString = phaseOptions.getValue();
    String newWaterInterval = numberOfDays.getValue();

    String message =
        plantOverview.editPlant(selectedPlant.getName(), newPhaseString, newWaterInterval);
    String[] types = message.split("[:,\n]");
    Alert a1 = new Alert(null, message);
    if (types.length > 2 && ((types[0].equals("Success") && types[2].equals("Error"))
        || (types[0].equals("Error") && types[2].equals("Success")))) {
      a1.setAlertType(AlertType.WARNING);
    } else if (types[0].equals("Success")) {
      a1.setAlertType(AlertType.INFORMATION);
    } else {
      a1.setAlertType(AlertType.ERROR);
    }
    currentStage.close();
    a1.show();
    appController.writeToFile(plantOverview);
    appController.filteredOverview();
  }
  
  /**
   * Deletes a plant from the plant overview and performs additional actions.
   * This method deletes a plant based on the plant name displayed in the plantNameLabel.
   * After deleting the plant, it hides the stage and displays an information alert
   * indicating the successful deletion of the plant. Finally, it calls the writeToFile
   * method to save changes made to the plantoverview.
   */
  public void deletePlant() {
    plantOverview.deletePlant(selectedPlant.getName());
    //why hide here, and close in editPlant?
    currentStage.close();
    Alert a1 = new Alert(AlertType.INFORMATION, "Successfully deleted the plant");
    a1.show();
    appController.writeToFile(plantOverview);
    appController.filteredOverview();
  }

  /**
   * Waters the currently selected plant and manages related actions.
   * If the selected plant needs water, it updates the last watering timestamp,
   * displays an information alert, and closes the current stage.
   * If the selected plant doesn't need water, it asks the user for confirmation,
   * and based on their choice, it may still update the last watering timestamp,
   * display a warning alert, and suggest watering the plant less frequently.
   * After the watering or confirmation, it saves data to a file and displays
   * the plant care overview in the application.
   */
  public void waterPlant() {
    if (selectedPlant.needsWater()) {
      selectedPlant.setLastWatered(new Date());
      Alert a1 = new Alert(AlertType.INFORMATION, selectedPlant.getName() + " has been watered:)");
      a1.show();
      currentStage.close();
    } else {
      Alert a1 = new Alert(AlertType.WARNING, selectedPlant.getName()   
          + " does not need water. \nDo you still wanna water the plant?",
              ButtonType.NO, ButtonType.YES);
      a1.showAndWait();
      if (a1.getResult().getText().equals("Yes")) {
        selectedPlant.setLastWatered(new Date());
        Alert a2 = new Alert(AlertType.WARNING, selectedPlant.getName() 
            + " has been watered, but did not need it.\nTry watering this plant less frequently.");
        a2.show();
        currentStage.close();
      }
    }
    appController.writeToFile(plantOverview);
    appController.filteredOverview();
  } 

  /**
   * Sets the AppController instance for this class.
   * This method is used to associate an AppController with this class
   * for handling various operations.
   *
   * @param controller The AppController instance to be associated with this class.
   */
  protected void setAppController(AppController controller) {
    appController = controller;
  }
}
  
