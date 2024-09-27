package ui;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import core.Plant;
import core.PlantOverview;
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


/**
 * The `RemoteAppController` class is responsible for managing the user
 * interface and interacting with the application's data.
 */
public class RemoteAppController {

  @FXML private ListView<String> plantTable;

  @FXML private TextArea informationArea;

  @FXML private TextArea plantDescription;

  @FXML private Label plantNameLabel;
  
  @FXML private Button newPlant;

  @FXML private Button editPlant;

  @FXML private Button deletePlant;

  @FXML private ImageView monsteraPlant;
  
  @FXML private ChoiceBox<String> filterOptions;


  private RemotePlantOverviewAccess remotePlantOverviewAccess;
  
  private String API_ENDPOINT = "http://localhost:8080/greenhouse";
  /**
  * Initializes the remote controller after the FXML file has been loaded.
  */
  @FXML
  public void initialize() {
    this.remotePlantOverviewAccess = new RemotePlantOverviewAccess(URI.create(this.API_ENDPOINT));
    initializeFilterOption();
    showOverview();
  }

  /**
   * The method reads plant information from a file, clears the existing items in the TableView,
   * and populates it with plant names from the retrieved data.
   * If no plant information is available, the TableView remains empty.
   */
  public void showOverview() {
    showFilteredOverview(getRemotePlantOverview()); 
  }

  /**
   * Displays a filtered plant overview in a user interface.
   * This method clears the existing plant data from the ListView plantTable,
   * and then populates it with the filtered plant overview based on the provided
   * 'PlantOverview' object. Each plant in the overview is represented as a string,
   * and additional information is appended to the plant's name
   * if it requires watering.
   *
   * @param plantOverview The filtered 'PlantOverview' containing the plants to display.
   */
  public void showFilteredOverview(PlantOverview plantOverview) {
    plantTable.getItems().clear();
    if (!plantOverview.getPlantOverview().isEmpty()) {
      List<String> plantItems = plantOverview.getPlantOverview().stream() 
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
    String filter = filterOptions.getValue();
    PlantOverview filteredOverview = PlantOverview.filterPlantOverview(filter, getRemotePlantOverview()); 
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
      selectedPlant = getRemotePlantOverview().getPlantOverview().stream().filter(
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
    loader = new FXMLLoader(getClass().getResource("RemoteNewPlant.fxml"));
    Parent root = loader.load();
    RemoteNewPlantController remoteNewPlantController = loader.getController();
    //remoteNewPlantController.setAppController(this);
    remoteNewPlantController.setRemotePlantOverviewAccess(remotePlantOverviewAccess);
    Scene scene = new Scene(root);
    Stage secondStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    secondStage.setScene(scene);
  }

  /**
   * Returns a copy of the current remote plant overview.
   *
   * @return A new PlantOverview object containing a copy of the current remote plant overview data.
   */
  public PlantOverview getRemotePlantOverview() {
    return new PlantOverview(remotePlantOverviewAccess.getPlantOverview().getPlantOverview());
  }

  /**
   * Opens a pop-up window to display detailed information about a selected plant.
   *
   * @param selectedPlant The Plant object representing the selected plant to be displayed in the pop-up.
   * @throws IOException if an error occurs while loading the FXML file or initializing the pop-up window.
   */
  protected void openPopUp(Plant selectedPlant) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("RemotePlantPopUp.fxml"));
    Parent root = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(root));
    stage.initModality(Modality.APPLICATION_MODAL); 
    
    RemotePopUpController remotePopUpController = loader.getController();
    remotePopUpController.setStage(stage);
    remotePopUpController.setAppController(this);
    remotePopUpController.setSelectedPlant(selectedPlant);
    remotePopUpController.setRemotePlantOverviewAccess(this.remotePlantOverviewAccess);
    remotePopUpController.initializeScene();
  
    stage.showAndWait();
  }
}
