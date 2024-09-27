package json;

import com.fasterxml.jackson.databind.module.SimpleModule;
import core.Plant;
import core.PlantOverview;

/**
 * A custom Jackson module for serializing and deserializing greenhouse-related objects.
 * This class extends Jackson's `SimpleModule` and is designed to be used 
 * with Jackson's ObjectMapper to handle the serialization and 
 * deserialization of `Plant` and `PlantOverview` objects in a JSON format.
 * It registers custom serializers and deserializers for these classes.
 */
public class GreenhouseModule extends SimpleModule {
  private static final String NAME = "GreenhouseModule";

  /**
  * Constructs a new GreenhouseModule with custom serializers and deserializers.
  * The module is configured with a name and version information. 
  * It also registers custom serializers
  * and deserializers for the `Plant` and `PlantOverview` classes.
  */
  
  public GreenhouseModule() {
    super(NAME);
    // Register custom serializers for Plant and PlantOverview
    addSerializer(Plant.class, new PlantSerializer());
    addSerializer(PlantOverview.class, new PlantOverviewSerializer());

    // Register custom deserializers for Plant and PlantOverview
    addDeserializer(Plant.class, new PlantDeserializer());
    addDeserializer(PlantOverview.class, new PlantOverviewDeserializer());
  }
    
} 