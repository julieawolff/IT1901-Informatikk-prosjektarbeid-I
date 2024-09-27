package json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import core.Plant;
import core.PlantOverview;
import java.io.IOException;

/**
 * Custom JSON deserializer for deserializing a JSON representation of a PlantOverview object.
 * This class extends JsonDeserializer and provides custom deserialization logic
 * for converting JSON data into a PlantOverview instance.
 */
public class PlantOverviewDeserializer extends JsonDeserializer<PlantOverview> {
   
  private PlantDeserializer plantDeserializer = new PlantDeserializer();

  /**
    * Deserializes a JSON representation of a PlantOverview object.
    * This method is used to deserialize a JSON object into a PlantOverview instance.
    * It reads the JSON data from the provided JsonParser and constructs a PlantOverview
    * object from it. The expected JSON structure should have a "Plants" field containing
    * an array of plant objects.
    *
    * @param p     The JsonParser used to read the JSON data.
    * @param ctxt  The DeserializationContext for handling deserialization operations.
    * @return      A PlantOverview object deserialized from the JSON data, or null if the
    *              input JSON does not match the expected structure.
    * @throws IOException      If an I/O error occurs during JSON parsing.
    * @throws JacksonException If a Jackson-specific exception occurs during deserialization.
    */
  @Override
  public PlantOverview deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    TreeNode node;
    node = p.getCodec().readTree(p);
    if (node instanceof ObjectNode) {
      ObjectNode objectNode = (ObjectNode) node;
      PlantOverview overview = new PlantOverview();
      JsonNode plantsNode = objectNode.get("Plants");

      if (plantsNode instanceof ArrayNode) {
        for (JsonNode plantNode : ((ArrayNode) plantsNode)) {
          Plant plant = plantDeserializer.deserialize(plantNode);
          if (plant != null) {
            overview.addPlant(plant);
          }
        }
      }
      return overview;
    }
    return null;
  }
}
