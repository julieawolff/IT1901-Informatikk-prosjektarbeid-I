package json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import core.Plant;
import java.io.IOException;
import java.util.Date;

/**
 * Custom JSON deserializer for deserializing a JSON representation of a Plant object.
 * This class extends JsonDeserializer and provides custom deserialization logic
 * for converting JSON data into a Plant instance.
 */
public class PlantDeserializer extends JsonDeserializer<Plant> {

  /**
    * Deserializes a JSON representation of a Plant object.
    * This method is used to deserialize a JSON object into a Plant instance. It reads
    * the JSON data from the provided JsonParser, converts it to a JsonNode, and then
    * delegates the actual deserialization to the `deserialize(JsonNode)` method.
    *
    * @param p     The JsonParser used to read the JSON data.
    * @param ctxt  The DeserializationContext for handling deserialization operations.
    * @return      A Plant object deserialized from the JSON data.
    * @throws IOException      If an I/O error occurs during JSON parsing.
    * @throws JacksonException If a Jackson-specific exception occurs during deserialization.
    */
  @Override
  public Plant deserialize(JsonParser p, DeserializationContext ctxt) 
      throws IOException, JacksonException {
    TreeNode node = p.getCodec().readTree(p);
    return deserialize((JsonNode) node);
  }

  /**
    * Deserialize a JSON representation of a Plant object from a JsonNode.
    * This method is used to deserialize a JSON object into a Plant instance by extracting
    * relevant fields from a JsonNode. It expects the JsonNode to have the following structure:
    * {
    *     "Name": "...",
    *     "Phase": "...",
    *     "Water interval": "...",
    *     "Last watered": "...",
    *     "Creation date": "...",
    * }
    *
    * @param node The JsonNode representing the Plant object.
    * @return A Plant object deserialized from the JsonNode, or null.
    */
  public Plant deserialize(JsonNode node) {
    String name = "";
    String phase = "";
    int waterInterval = 0;
    Date lastWatered = new Date();
    Date creationDate = new Date();
    
      
    if (node instanceof ObjectNode objectNode) {
    
      JsonNode nameNode = objectNode.get("Name");
      if (nameNode instanceof TextNode) {
        name = nameNode.asText();
      }

      JsonNode phaseNode = objectNode.get("Phase");
      if (phaseNode instanceof TextNode) {
        phase = phaseNode.asText();
      }

      JsonNode waterNode = objectNode.get("Water interval");
      if (waterNode instanceof TextNode) {
        waterInterval = waterNode.asInt();
      }
      
      JsonNode lastWateredNode = objectNode.get("Last watered");
      if (lastWateredNode instanceof TextNode) {
        String lastWateredString = lastWateredNode.asText();
        lastWatered = Plant.fromStringToDate(lastWateredString);
      }

      JsonNode creationDateNode = objectNode.get("Creation date");
      if (creationDateNode instanceof TextNode) {
        String creationDateString = creationDateNode.asText();
        creationDate = Plant.fromStringToDate(creationDateString);
      }
      Plant p = new Plant(name, phase, waterInterval, creationDate);
      p.setLastWatered(lastWatered);
      if (!name.isEmpty() && !phase.isEmpty() && waterInterval != 0) {
        return p;
      }
    }
    return null;
  }
}
