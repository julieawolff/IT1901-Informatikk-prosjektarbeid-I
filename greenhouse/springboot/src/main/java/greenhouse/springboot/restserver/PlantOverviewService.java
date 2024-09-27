package greenhouse.springboot.restserver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import core.Plant;
import core.PlantOverview;
import json.PlantPersistence;


/**
 * Service class for managing and interacting with plant overview data.
 * This class provides methods for initializing, retrieving, and automatically saving
 * plant overview data. It uses a PlantPersistence object to read and save data from/to
 * a data storage location.
*/
@Service
public class PlantOverviewService {

  private PlantOverview plantOverview;
  private PlantPersistence plantPersistence;

  /**
    * Initializes a PlantOverviewService by reading plant overview data from a JSON file.
    * An instance of PlantOverviewService is created with the following steps:
    * 1. A new PlantPersistence object is initialized.
    * 2. Plant overview data is read from the "../ui/src/main/resources/ui/plants.json" file.
    * 3. If an IOException occurs during file reading, an error message is printed to the standard error stream.
    * 4. The save file for PlantPersistence is set.
  */
  public PlantOverviewService() {
    this.plantPersistence = new PlantPersistence();
    try (FileReader fileReader = new FileReader(new File("../ui/src/main/resources/ui/plants.json"), StandardCharsets.UTF_8)) {
      this.plantOverview = plantPersistence.readPlantOverview(fileReader);
    } catch (IOException e) {
      System.err.println(e.toString());
    }
    this.plantPersistence.setSaveFile();
  }

  /**
   * Creates a new PlantOverview object and populates it with a copy of the plant data
   * from the current instance's plant overview. The copied data ensures that the original plant
   * overview remains unaltered and can be safely accessed by the caller.
   *
   * @return A new PlantOverview object containing a copy of the plant data.
   *         If an exception occurs during the copy process, an empty PlantOverview object is returned.
   */
  public PlantOverview getPlantOverview() {
    PlantOverview plantOverviewCopy = new PlantOverview();
    try {
      for (Plant plant : this.plantOverview.getPlantOverview()) {
        plantOverviewCopy.addPlant(new Plant(plant.getName(), plant.getPhase(), plant.getWaterInterval(), plant.getCreationDate(), plant.getLastWatered()));
      }
    } catch (Exception e) {
      return new PlantOverview();
    }
    return plantOverviewCopy;
  }
  
  /**
  * Takes a PlantOverview object and attempts to automatically save it to a data
  * storage location. The method may print informative messages to the standard output and
  * standard error streams to report the process and any errors encountered during the operation.
  *
  * @param plantOverview The PlantOverview to be auto-saved.
  */
  public void autoSavePlantOverview(PlantOverview plantOverview) {
    try {
      plantPersistence.savePlantOverview(plantOverview);
    } catch (IllegalStateException | IOException e) {
      System.err.println("Couldn't auto-save PlantOverview: " + e);
    }
  }
}
