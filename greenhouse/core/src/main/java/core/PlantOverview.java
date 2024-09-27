package core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * The `PlantOverview` class represents a collection of `Plant` objects.
 * It allows you to manage and retrieve information about various plants.
 */
public class PlantOverview {
  private List<Plant> plantOverview;

  /**
   * Constructor that creates a new arraylist.
   */
  public PlantOverview() {
    plantOverview = new ArrayList<>();
  }

  /**
   * This constructor creates a new PlantOverview object using the provided list of
   * plant objects. It makes a shallow copy of the list, allowing for a new instance
   * to be created without modifying the original list.
   *
   * @param plantOverview A list of plant objects used to initialize the PlantOverview.
   */
  public PlantOverview(List<Plant> plantOverview) {
    this.plantOverview = new ArrayList<>(plantOverview);
  }

  /**
   * Adds a new Plant object to the plantOverview.
   *
   * @param newPlant the plant to be added
   */
  public void addPlant(Plant newPlant) {
    checkAddPlant(newPlant);
    plantOverview.add(newPlant);
  }


  /**
   * Checks if another plant has the same name,
   * and that all the inputfields have values.
   *
   * @param newPlant the plant to be checked
   */
  public void checkAddPlant(Plant newPlant) {
    if (getPlantNames().contains(newPlant.getName())) {
      throw new IllegalArgumentException("There already exists a plant named "
      + newPlant.getName());
    }
    if (newPlant.getName() == null || newPlant.getName().equals("")) {
      throw new IllegalArgumentException("Choose a name for your plant");
    }
    if (newPlant.getPhase() == null) {
      throw new IllegalArgumentException("Choose your plants growing phase");
    }
  }

  /**
   * Returns the plantOverview.
   *
   * @return a copy of the plantOverview.
   */
  public List<Plant> getPlantOverview() {
    return new ArrayList<>(plantOverview);
  }

  /**
   * Returns a copy of plantOverview filtered by a gived predicate.
   *
   * @param predicate the predicate to filter by
   * @return a list of plants that fulfilles the predicate.
   */
  public List<Plant> getPlantsByPredicate(Predicate<Plant> predicate) {
    return getPlantOverview().stream().filter(predicate).toList();
  }

  /**
   * Returns a list of plant names.
   *
   * @return a list of strings with plant names.
   */
  private List<String> getPlantNames() {
    return getPlantOverview().stream().map(p -> p.getName()).toList();
  }

  /**
   * Retrieves a plant object by its name from the plant overview.
   *
   * @param name The name of the plant to search for.
   *
   * @return The plant object with the specified name, or null if not found.
   */
  public Plant getPlantByName(String name) {
    for (Plant p : plantOverview) {
      if (name.equals(p.getName())) {
        return p;
      }
    }
    return null;
  }

  /**
   * Edits the details of a plant, including its growth phase and water interval,
   * based on the provided values.
   *
   * @param name The name of the plant to be edited.
   * @param newPhase The new growth phase to assign to the plant, or null if no change is intended.
   * @param newWaterInterval The new water interval (in days) to assign to the plant,
   or null if no change is intended.
   *
   * @return A message indicating the outcome of the edit operation.
   */
  public String editPlant(String name, String newPhase, String newWaterInterval) {
    Plant oldPlant = getPlantByName(name);

    if (newPhase == null && newWaterInterval == null) {
      return "Error: No changes selected";
    }

    String message = "";

    if (newPhase != null) {
      if (oldPlant.getPhase().equals(newPhase)) {
        message += "Error: Selected phase matched old phase.\n";
      } else if (isPhaseDowngrade(oldPlant, newPhase)) {
        message += "Error: You cannot downgrade the plant's phase.\n";
      } else {
        oldPlant.setPhase(newPhase);
        message += "Success: Phase has been updated.\n";
      }
    }

    if (newWaterInterval != null) {
      int newInterval = Integer.parseInt(newWaterInterval);
      if (oldPlant.getWaterInterval() == newInterval) {
        message += "Error: Selected water interval matched old water interval.";
      } else {
        oldPlant.setWaterInterval(newInterval);
        message += "Success: Water interval has been updated.";
      }
    }
    return message;
  }

  /**
   * Filters a PlantOverview based on the specified filter criteria.
   *
   * @param filter The filter criteria to apply to the PlantOverview.
   * @param overview The PlantOverview to be filtered.
   * @return A new PlantOverview containing the plants that match the specified filter criteria.
   */
  public static PlantOverview filterPlantOverview(String filter, PlantOverview overview) {
    if (filter == null) {
      return new PlantOverview(overview.getPlantOverview());
    }
    Predicate<Plant> filterPredicate;
    switch (filter) {
      case "Seed":
        filterPredicate = p -> p.getPhase().equals("Seed");
        break;
      case "Young plant":
        filterPredicate = p -> p.getPhase().equals("Young plant");
        break;
      case "Grown plant":
        filterPredicate = p -> p.getPhase().equals("Grown plant");
        break;
      case "Needs water":
        filterPredicate = Plant::needsWater;
        break;
      case "Is hydrated":
        filterPredicate = p -> !p.needsWater();
        break;
      default:
        return new PlantOverview(overview.getPlantOverview());
    }
    return new PlantOverview(overview.getPlantsByPredicate(filterPredicate));
  }

  /**
   * Deletes a plant from the plant overview based on its name.
   * This method takes the name of a plant as a parameter, searches for a plant
   * with the matching name in the plantoverview list, and removes it if found. If
   * the plant with the given name is not present in the plantoverview, no
   * action is taken.
   *
   * @param name The name of the plant to be deleted.
   */
  public void deletePlant(String name) {
    Plant p = getPlantByName(name);
    if (plantOverview.contains(p)) {
      plantOverview.remove(p);
    }
  }

  /**
   * This method inspects the state of the plants in the plantOverview and creates a status message 
   * based on the following:
   * - If there are no plants in the greenhouse, it welcomes the user to their new greenhouse.
   * - If all plants are adequately watered, it communicates that all plants are in a healthy state.
   * - If some plants require watering, it lists the names of those plants and indicates their need 
   * for water.
   *
   * @return A status message 
   */
  public String status() {
    StringBuilder result = new StringBuilder();
    if (plantOverview.isEmpty()) {
      return result.append("Welcome to your Greenhouse!").toString(); 
    }
    
    List<Plant> plants = getPlantsByPredicate(p -> p.needsWater());
    if (plants.isEmpty()) {
      result.append("All your plants are happy today<33"); 
    } else {
      for (int i = 0; i < plants.size(); i++) {
        result.append(plants.get(i).getName());

        if (i < plants.size() - 2) {
          result.append(", "); // Add a comma if it's not the second-to-last or last element
        } else if (i == plants.size() - 2) {
          result.append(" and "); // Add "and" before the last element
        }
      } 
      result.append(" need(s) water.");
    }
    return result.toString();
  }

  /**
   * Checks if changing the growth phase of a plant to
   * a specified new phase would result in a downgrade.
   *
   * @param plant The plant whose growth phase is to be compared.
   * @param newPhase The new growth phase being considered for the plant.
   *
   * @return true if changing to the specified new growth phase would be considered a downgrade;
   false otherwise.
   */
  private boolean isPhaseDowngrade(Plant plant, String newPhase) {
    return (plant.getPhase().equals("Young plant") && newPhase.equals("Seed"))
        || (plant.getPhase().equals("Grown plant") && (newPhase.equals("Seed")
        || newPhase.equals("Young plant")));
  }
}
