package core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * The `Plant` class represents a plant with various attributes such as 
 * name, growth phase, water interval, creation date, and last watered date.
 */
public class Plant {
  private String name;
  private String phase;
  private int waterInterval;
  private Date creationDate;
  private Date lastWatered;
  
  /**
   * Constructs a new `Plant` object with the given attributes.
   *
   * @param name          The name of the plant.
   * @param phase         The growth phase of the plant.
   * @param waterInterval The number of days between watering.
   * @param creationDate  The date when the plant was created.
   * @param lastWatered   The date when the plant was last watered
   */
  public Plant(String name, String phase, int waterInterval, Date creationDate, Date lastWatered) {
    this.name = name;
    this.phase = phase;
    this.waterInterval = waterInterval;
    this.creationDate = new Date(creationDate.getTime());
    this.lastWatered = new Date(lastWatered.getTime());
  }

  /**
   * Constructs a new `Plant` object with the given attributes.
   *
   * @param name          The name of the plant.
   * @param phase         The growth phase of the plant.
   * @param waterInterval The number of days between watering.
   * @param creationDate  The date when the plant was created.
   */
  public Plant(String name, String phase, int waterInterval, Date creationDate) {
    this(name, phase, waterInterval, creationDate, creationDate);
  }

  /**
   * Constructs a new `Plant` object with the given attributes, using the current 
   * date as the creation date.
   *
   * @param name          The name of the plant.
   * @param phase         The growth phase of the plant.
   * @param waterInterval The number of days between watering.
   */
  public Plant(String name, String phase, int waterInterval) {
    this(name, phase, waterInterval, new Date());
  }

  /**
   * This constructor creates a new Plant object using the name, phase, and watering
   * interval properties of the selectedPlant, effectively cloning the selectedPlant.
   *
   * @param selectedPlant The selectedPlant from which properties are copied.
   */
  public Plant(Plant selectedPlant) {
    this(selectedPlant.getName(), selectedPlant.getPhase(), selectedPlant.getWaterInterval(),
        selectedPlant.getCreationDate());
  }

  /**
   * Retrieves the name of the plant.
   *
   * @return The name of the plant.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the plant.
   *
   * @param newName The new name to set for the plant.
   */
  public void setName(String newName) {
    this.name = newName;
  }

  /**
   * Retrieves the growth phase of the plant.
   *
   * @return The growth phase of the plant.
   */
  public String getPhase() {
    return this.phase;
  }

  /**
   * Sets the growth phase of the plant.
   *
   * @param newPhase The new growth phase to set for the plant.
   */
  public void setPhase(String newPhase) {
    this.phase = newPhase;
  }

  /**
   * Retrieves the number of days between watering for the plant.
   *
   * @return The water interval in days.
   */
  public int getWaterInterval() {
    return this.waterInterval;
  }

  /**
   * Sets the number of days between watering for the plant.
   *
   * @param newWaterInterval The new water interval to set for the plant.
   */
  public void setWaterInterval(int newWaterInterval) {
    this.waterInterval = newWaterInterval;
  }

  /**
   * Retrieves the creation date of the plant.
   *
   * @return The creation date of the plant as a `Date` object.
   */
  public Date getCreationDate() {
    return new Date(this.creationDate.getTime());
  }

  
  public void setCreationDate(Date newCreationDate) {
    this.creationDate = new Date(this.creationDate.getTime());
  }

  /**
   * Sets the last watered date of the plant.
   *
   * @param newDate The new date to set as the last watered date.
   */
  public void setLastWatered(Date newDate) {
    this.lastWatered = new Date(newDate.getTime());
  }

  /**
   * Retrieves the last watered date of the plant.
   *
   * @return The last watered date of the plant as a `Date` object.
   */
  public Date getLastWatered() {
    return new Date(this.lastWatered.getTime());
  }

  /**
   * Calculates the lifespan of the plant in days.
   *
   * @return The number of days since the plant's creation.
   */
  public int lifeSpan() {
    return daysBetween(getCreationDate(), new Date());
  }

  /**
   * Checks if the plant needs watering based on the last watered date and water interval.
   *
   * @return `true` if the plant needs watering, `false` otherwise.
   */
  public boolean needsWater() {
    return daysBetween(lastWatered, new Date()) >= waterInterval;
  }
  
  /**
   * Generates a detailed string representation of the `Plant` object.
   *
   * @return A string containing the plant's name, phase, water interval, lifespan, 
   creation date, and last watered date.
   */
  @Override
  public String toString() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    return "Name: " + getName() + "\n" + "Phase: " + getPhase() + "\n" 
        + "Water interval: " + getWaterInterval() + " day(s) \n" + "Lifespan: "
        + lifeSpan() + "\n" + "Creation date: " + dateFormat.format(getCreationDate())
        + "\n" + "Last watered: " + dateFormat.format(getLastWatered());
  }

  /**
   * Calculates the number of days between two dates.
   *
   * @param firstDate  The first date.
   * @param secondDate The second date.
   * @return The number of days between the two dates.
   */
  public static int daysBetween(Date firstDate, Date secondDate) {
    Long daysBetween = ChronoUnit.DAYS.between(firstDate.toInstant(), secondDate.toInstant());
    return daysBetween.intValue();
  }

  /**
   * Converts a `Date` object to a formatted date string.
   *
   * @param date The date to convert.
   * @return The formatted date string.
   */
  public static String toStringDate(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return dateFormat.format(date);
  }

  /**
   * Converts a formatted date string to a `Date` object.
   *
   * @param s The date string to parse.
   * @return The parsed `Date` object, or `null` if parsing fails.
   */
  public static Date fromStringToDate(String s) {
    Date date = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      date = dateFormat.parse(s);
    } catch (ParseException e) {
      System.err.println(e.toString());
    }
    return date;
  }
}
