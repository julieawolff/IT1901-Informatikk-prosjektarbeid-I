package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.PlantOverview;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The `PlantPersistence` class provides functionality for handling and persisting plant data.
 * It includes methods for reading and writing PlantOverview objects in JSON format.
 * This class initializes an ObjectMapper with a custom GreenhouseModule for JSON serialization
 * and deserialization of plant data. It also offers methods to read and write PlantOverview objects
 * to and from Readers and Writers, respectively.
 */
public class PlantPersistence {

  private ObjectMapper mapper;

  /**
  * This constructor sets up the PlantPersistence object for handling plant data.
  * It creates a new ObjectMapper and registers a custom module called GreenhouseModule.
  * The ObjectMapper is used for serializing and deserializing plant data in JSON format.
  */
  public PlantPersistence() {
    mapper = new ObjectMapper();
    mapper.registerModule(new GreenhouseModule());
  }
 
  /**
  * Reads and deserializes a PlantOverview object from the provided Reader.
  * This method reads data from the given Reader and deserializes it into a PlantOverview object.
  * If the Reader is not ready or contains no data, an empty PlantOverview object is returned.
  *
  * @param reader The Reader containing the data to be deserialized.
  * @return A PlantOverview object deserialized from data, or empty one if no data is available.
  * @throws IOException If an I/O error occurs during reading or deserialization.
  */
  public PlantOverview readPlantOverview(Reader reader) throws IOException {
    if (!reader.ready()) {
      return new PlantOverview();
    }
    return mapper.readValue(reader, PlantOverview.class);
  }

  /**
  * Serializes and writes a PlantOverview object to the provided Writer.
  * This method serializes the given PlantOverview object and writes it to the provided Writer.
  * The output is formatted with a default pretty printer for improved readability.
  *
  * @param writer The Writer to which the serialized data will be written.
  * @param overview The PlantOverview object to be serialized and written.
  * @throws IOException If an I/O error occurs during serialization or writing.
  */
  public void writePlantOverview(Writer writer, PlantOverview overview) throws IOException {
    mapper.writerWithDefaultPrettyPrinter().writeValue(writer, overview);
  }

  /**
  * Creates a module for Greenhouse object serialization and deserialization.
  *
  * @return The created Jackson module.
  */
  public static SimpleModule createJacksonModule() {
    return new GreenhouseModule();
  }

  
  private Path saveFilePath = null;

  //må endres!! se hvordan vi har gjort det i kontrolleren
  public void setSaveFile() {
    this.saveFilePath = Paths.get("../ui/src/main/resources/ui/plants.json");
  }

  /**
   * Saves a TodoModel to the saveFilePath in the user.home folder.
   *
   * @param todoModel the TodoModel to save
   */
  public void savePlantOverview(PlantOverview plantOverview) throws IOException, IllegalStateException {
    if (saveFilePath == null) {
      throw new IllegalStateException("Save file path is not set, yet");
    }
    try (Writer writer = new FileWriter(saveFilePath.toFile(), StandardCharsets.UTF_8)) {
      writePlantOverview(writer, plantOverview);
    }
  }
  


}
