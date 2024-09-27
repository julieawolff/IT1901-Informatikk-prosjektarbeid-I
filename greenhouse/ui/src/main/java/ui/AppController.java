package ui;

import core.Plant;
import core.PlantOverview;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import json.PlantPersistence;

/**
 * The `AppController` class is responsible for managing the user
 * interface and interacting with the application's data.
 */
public class AppController {

  @FXML private ListView<String> plantTable;

  @FXML private TextArea informationArea;

  @FXML private TextArea plantDescription;

  @FXML private Label plantNameLabel;
  
  @FXML private Button newPlant;

  @FXML private Button editPlant;

  @FXML private Button deletePlant;

  @FXML private ImageView monsteraPlant;
  
  @FXML private ChoiceBox<String> phaseOptions;

  @FXML private ChoiceBox<String> numberOfDays;
  
  @FXML private ChoiceBox<String> filterOptions;

  @FXML String isTest;

  private PlantOverview plantOverview;
  private PlantPersistence persistence = new PlantPersistence();
  private String pathName;

  /**
   * Initializes the controller after the FXML file has been loaded.
   */
  @FXML
  void initialize() {
    setPathName();
    initializeFilterOption();
    showOverview();
  }

  /**
   * The method reads plant information from a file, clears the existing items in the TableView,
   * and populates it with plant names from the retrieved data.
   * If no plant information is available, the TableView remains empty.
   */
  public void showOverview() {
    readFromFile();
    showFilteredOverview(plantOverview);
  }

  /**
   * Displays a filtered plant overview in a user interface.
   * This method clears the existing plant data from the ListView plantTable,
   * and then populates it with the filtered plant overview based on the provided
   * 'PlantOverview' object. Each plant in the overview is represented as a string,
   * and additional information is appended to the plant's name
   * if it requires watering.
   *
   * @param overview The filtered 'PlantOverview' containing the plants to display.
   */
  public void showFilteredOverview(PlantOverview overview) {
    plantTable.getItems().clear();
    if (!overview.getPlantOverview().isEmpty()) {
      List<String> plantItems = overview.getPlantOverview().stream()
            .map(p -> p.getName() + (p.needsWater() ? " NEEDS WATER" : ""))
            .collect(Collectors.toList());

      plantTable.getItems().setAll(plantItems);
    }
  }

  /**
   * Displays a filtered overview of plants based on the selected filter option.
   * This method reads plant information from a file, retrieves the selected filter
   * option from the user interface, and filters the plant data accordingly. It then
   * displays the filtered plant overview to the user.
   */
  public void filteredOverview() {
    readFromFile();
    String filter = filterOptions.getValue();
    PlantOverview filteredOverview = PlantOverview.filterPlantOverview(filter, plantOverview);
    showFilteredOverview(filteredOverview);
  }

  /**
   * Initializes the filter options for filtering plants in a user interface.
   * This method populates a dropdown or choice box with various filter options for
   * categorizing plants, such as "All plants," "Seed," "Young plant," "Grown plant,"
   * "Needs water," and "Is hydrated." Users can select a filter option from the list to
   * view and filter the plants accordingly.
   */
  public void initializeFilterOption() {
    ObservableList<String> filterOptionsList = FXCollections.observableArrayList(
        "All plants", "Seed", "Young plant", "Grown plant", "Needs water", "Is hydrated");
    filterOptions.getItems().addAll(filterOptionsList);
  }

  /**
   * Opens a pop-up window to display detailed information about the selected plant.
   * If a plant is selected in the TableView, this method retrieves the corresponding Plant object
   * based on the selected plant's name and then opens a pop-up window to show its details.
   */
  public void selectedPlant() {
    Plant selectedPlant = null;
    String selectedPlantString = plantTable.getSelectionModel().getSelectedItem();
    if (selectedPlantString != null) {
      String[] selectedPlantStringArray = selectedPlantString.split("\s");
      selectedPlant = plantOverview.getPlantOverview().stream().filter(
        p -> selectedPlantStringArray[0].equals(p.getName())).findFirst().get();
      try {
        openPopUp(selectedPlant);
      } catch (IOException e) {
        System.err.println(e.toString());
      }
    }
  }

  /**
   * Handles the action when the "New Plant" button is pushed.
   * This method is called when the "New Plant" button is 
   * clicked in the user interface.
   * It loads a new FXML view, "NewPlant.fxml" or "NewPlant_Test.fxml"
   * based on the `isTest` flag, and displays it in a new window.
   *
   * @param event The ActionEvent associated with the button click.
   * @throws IOException if there is an error loading the FXML file.
   */
  public void openNewPlant(ActionEvent event) throws IOException {
    FXMLLoader loader;
    if (getIsTest() == null) {
      loader = new FXMLLoader(getClass().getResource("NewPlant.fxml"));
    } else {
      loader = new FXMLLoader(getClass().getResource("NewPlant_Test.fxml"));
    }
    Parent root = loader.load();
    NewPlantController controller = loader.getController();
    controller.setAppController(this);
    Scene scene = new Scene(root);
    Stage secondStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    secondStage.setScene(scene);
  }

  /**
   * Sets the `pathname` instance variable based on the current working directory.
   */
  public void setPathName() {
    if (getIsTest() == null) {
      pathName = "src/main/resources/ui/plants.json";
    } else {
      pathName = "src/test/resources/ui/test-app.json";
    }
  }

  /**
   * Retrieves the current pathname used for loading FXML files.
   *
   * @return The pathname for loading FXML files.
   */
  public String getPathName() {
    return pathName;
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
   * Returns a copy of the current plant overview.
   *
   * @return A new PlantOverview object containing a copy of the current plant overview data.
   */
  public PlantOverview getPlantOverview() {
    return new PlantOverview(plantOverview.getPlantOverview());
  }

  /**
   * Opens a pop-up window to display detailed information about a selected plant.
   *
   * @param selectedPlant The Plant object representing the 
   selected plant to be displayed in the pop-up.
   * @throws IOException if an error occurs while 
   loading the FXML file or initializing the pop-up window.
   */
  protected void openPopUp(Plant selectedPlant) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("PlantPopUp.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL); 
    
    PopUpController controller = loader.getController();
    controller.setStage(stage);
    controller.setAppController(this);
    controller.setSelectedPlant(selectedPlant);
    controller.initializeScene();
  
    stage.showAndWait();
  }

  /**
   * This method opens and reads data from a file specified by the current path name, and it
   * uses the Persistence class to deserialize the data into the `plantOverview` object.
   *
   * @throws IOException if an error occurs while reading the file or deserializing the data.
   */
  protected void readFromFile() {
    try (FileReader fileReader = new FileReader(new File(getPathName()), StandardCharsets.UTF_8)) {
      plantOverview = persistence.readPlantOverview(fileReader);
    } catch (IOException e) {
      System.err.println(e.toString());
    }
  }

  /**
   * This method takes the `overview` parameter and uses the Persistence class to serialize
   * and write the data to the file specified by the current path name.
   *
   * @param overview The PlantOverview object to be written to the file.
   * @throws IOException if an error occurs while writing the data to the file.
   */
  protected void writeToFile(PlantOverview overview) {
    try (FileWriter fileWriter = new FileWriter(new File(getPathName()), StandardCharsets.UTF_8);) {
      persistence.writePlantOverview(fileWriter, overview);
    } catch (IOException e) {
      System.err.println(e.toString());
    }   
  }
}
