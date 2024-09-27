package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Plant;
import java.io.IOException;

/**
 * Serializer for converting a Plant object to a JSON representation.
 * This class extends Jackson's JsonSerializer and is responsible for serializing a Plant object
 * into a JSON format. It writes the Plant object's properties as 
 * fields to a JsonGenerator, creating a JSON object with
 * "Name," "Phase,", "Water interval", "Last watered" and "Creation date" fields.
 */
public class PlantSerializer extends JsonSerializer<Plant> {

  /**
  * Serialize a Plant object to a JSON representation.
  * This method is used to serialize a Plant object into a JSON format. It takes a Plant
  * instance and writes its properties as fields to a JsonGenerator, creating a JSON object
  * with "Name," "Phase,", "Water interval", "Last watered" and "Creation date" fields.
  *
  * @param plant       The Plant object to be serialized.
  * @param gen        The JsonGenerator used to write the JSON data.
  * @param serializers The SerializerProvider for handling serialization operations.
  * @throws IOException If an I/O error occurs during JSON writing.
  */
  @Override
  public void serialize(Plant plant, JsonGenerator gen, SerializerProvider serializers)
      throws IOException {
    gen.writeStartObject();
    gen.writeStringField("Name", plant.getName());
    gen.writeStringField("Phase", plant.getPhase());
    gen.writeStringField("Water interval", Integer.toString(plant.getWaterInterval()));
    gen.writeStringField("Last watered", Plant.toStringDate(plant.getLastWatered()));
    gen.writeStringField("Creation date", Plant.toStringDate(plant.getCreationDate()));
    gen.writeEndObject();
  } 
}

