package greenhouse.springboot.restserver;

  import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import core.Plant;
import core.PlantOverview;

/**
 * This class defines a REST API controller for managing PlantOverview data.
 */
@RestController
@RequestMapping(PlantOverviewController.GREENHOUSE_SERVICE_PATH)
public class PlantOverviewController {

  public static final String GREENHOUSE_SERVICE_PATH = "greenhouse";

  @Autowired
  private PlantOverviewService plantOverviewService;

  /**
   * Autosaves the updated plant overview.
   *
   * @param plantOverview The PlantOverview to be autosaved.
   */
  private void autoSavePlantOverview(PlantOverview plantOverview) {
    this.plantOverviewService.autoSavePlantOverview(plantOverview);
    this.plantOverviewService = new PlantOverviewService();
  }

  /**
   * Retrieves a plant by its name.
   *
   * @param name The name of the plant to retrieve.
   * @return The Plant object corresponding to the given name.
   */
  @GetMapping("/get")
  public Plant getPlant(@RequestParam String name) {
    return this.plantOverviewService.getPlantOverview().getPlantByName(name);
  }

  /**
   * Retrieves a plant overview.
   *
   * @param name The name of the plant to retrieve.
   * @return The Plant Overview object retrieved from PlantOverviewService
   */
  @GetMapping("/getPlantOverview")
  public PlantOverview getPlantOverview() {
    return this.plantOverviewService.getPlantOverview();
  }

  /**
   * Creates and adds a new plant to the plant overview.
   *
   * @param name The name of the new plant.
   * @param phase The growth phase of the new plant.
   * @param waterInterval The watering interval for the new plant.
   * @return The newly created Plant object.
   * @throws IllegalArgumentException If any of the input parameters are invalid.
   */
  @PostMapping("/post")
  public Plant postPlant(@RequestParam String name,
                                @RequestParam String phase,
                                @RequestParam int waterInterval) {
    if (waterInterval == 0){
      throw new IllegalArgumentException("Water interval cannot be 0");
    }
    Plant plant = new Plant(name, phase, waterInterval); 
    PlantOverview newPlantOverview = this.plantOverviewService.getPlantOverview();
    newPlantOverview.addPlant(plant);
    autoSavePlantOverview(newPlantOverview);
    return plant;
  }


  /**
   * Modifies plant details in the plant overview.
   *
   * @param name The name of the plant to modify.
   * @param phase The new growth phase for the plant (optional, set to "empty" if not provided).
   * @param waterInterval The new watering interval for the plant (optional, set to "empty" if not provided).
   * @param water A flag indicating whether to manually trigger watering for the plant (water if "True").
   * @return The updated Plant object.
   * @throws IllegalArgumentException If any of the input parameters are invalid.
   */
  @PutMapping("/put")
  public String putPlant(@RequestParam String name, 
                        @RequestParam(defaultValue = "empty") String phase, 
                        @RequestParam(defaultValue = "empty") String waterInterval,
                        @RequestParam(defaultValue = "empty") String water) {
    PlantOverview editedPlantOverview = this.plantOverviewService.getPlantOverview();

    String phaseResult = phase;
    String waterIntervalResult = waterInterval;
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("Name cannot be null");
    }
    if (phase.equals("empty")) {
      phaseResult = null;
    }
    if (waterInterval.equals("empty")) {
      waterIntervalResult = null;
    }
    if (water.equals("True")) {
      editedPlantOverview.getPlantByName(name).setLastWatered(new Date());
    }
    String message = editedPlantOverview.editPlant(name, phaseResult, waterIntervalResult);
    autoSavePlantOverview(editedPlantOverview);
    return message;
  }

  /**
   * Deletes a plant from the plant overview.
   *
   * @param name The name of the plant to be removed.
   * @return True if the plant is successfully removed, false otherwise.
   */
  @DeleteMapping ("/delete")
  public boolean deletePlant(@RequestParam String name) {
    try {
      PlantOverview editedPlantOverview = this.plantOverviewService.getPlantOverview();
      editedPlantOverview.deletePlant(name); 
      autoSavePlantOverview(editedPlantOverview);
    } catch (Exception e) {
      return false;
    }
    return true;
  }
}
