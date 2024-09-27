package json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import core.Plant;
import core.PlantOverview;
import java.io.IOException;

/**
 * Serializer for converting a PlantOverview object to a JSON representation.
 * This class extends Jackson's JsonSerializer and is responsible for serializing a PlantOverview
 * object into a structured JSON format. 
 */
public class PlantOverviewSerializer extends JsonSerializer<PlantOverview> {
  /**
    * Serialize a PlantOverview object to a JSON representation.
    * This method is used to serialize a PlantOverview object into a JSON format. It takes a
    * PlantOverview instance and writes its contents to a JsonGenerator in a structured JSON
    * format. The JSON representation will have a "Plants" field containing an array of Plant
    * objects.
    *
    * @param overview    The PlantOverview object to be serialized.
    * @param jsonGen     The JsonGenerator used to write the JSON data.
    * @param serializers The SerializerProvider for handling serialization operations.
    * @throws IOException If an I/O error occurs during JSON writing.
    */
  @Override
  public void serialize(PlantOverview overview, JsonGenerator jsonGen, 
      SerializerProvider serializers) throws IOException {
    jsonGen.writeStartObject();
    jsonGen.writeArrayFieldStart("Plants");
    for (Plant p : overview.getPlantOverview()) {
      jsonGen.writeObject(p);
    }
    jsonGen.writeEndArray();
    jsonGen.writeEndObject();
  }
}
