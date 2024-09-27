package ui;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.Date;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Plant;
import core.PlantOverview;
import json.GreenhouseModule;


/**
 * Represents a class for accessing remote plant overview information.
 * Provides methods for retrieving, updating, creating, and deleting plant data
 * through a remote API using HTTP requests.
 */
public class RemotePlantOverviewAccess {

  private URI endpointURI;
  private static final String APPLICATION_JSON = "application/json";
  private static final String ACCEPT_HEADER = "Accept";
  private static final String CONTENT_TYPE_HEADER = "Content-Type";
  private ObjectMapper mapper = new ObjectMapper();

  /**
   * Constructs a new RemotePlantOverviewAccess instance with the specified endpoint URI.
   *
   * @param endpointURI The URI of the remote plant overview API.
   */
  public RemotePlantOverviewAccess(URI endpointURI) {
    this.endpointURI = endpointURI;
    mapper.registerModule(new GreenhouseModule());
  }

  /**
   * Resolves the given URI relative to the endpoint URI for accounts.
   *
   * @param uri The URI to resolve.
   * @return The resolved URI.
   */
  public URI resolveURI(String uri) {
    URI resolvedEndpointURI = endpointURI.resolve(uri);
    return resolvedEndpointURI;
  }

  /**
   * Retrieves plant information by name from the remote API.
   *
   * @param name The name of the plant to retrieve.
   * @return The Plant object representing the retrieved plant data.
   * @throws IllegalArgumentException If there is no plant with the specified name.
   */
  public Plant getPlant(String name) {
    String uri = "/greenhouse/get?";
    uri += "name=" + name;
    try {
      URI resolvedURI = resolveURI(uri);
      HttpRequest httpRequest = HttpRequest
        .newBuilder(resolvedURI)
        .header(ACCEPT_HEADER, APPLICATION_JSON)
        .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
        .GET()
        .build();
    
      final HttpResponse<String> httpResponse = HttpClient.newBuilder()
        .build()
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());
      
      String response = httpResponse.body();
      return mapper.readValue(response, Plant.class);

    } catch(Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("There is no plant called " + name + ".");
    }
  }

  /**
   * Retrieves the overview of all plants from the remote API.
   *
   * @return The PlantOverview object representing the overview of all plants.
   * @throws IllegalArgumentException If there is an issue with building or sending the HttpRequest.
   */
  public PlantOverview getPlantOverview() {
    String uri = "/greenhouse/getPlantOverview";
    try {
      URI resolvedURI = resolveURI(uri);
      HttpRequest httpRequest = HttpRequest
        .newBuilder(resolvedURI)
        .header(ACCEPT_HEADER, APPLICATION_JSON)
        .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
        .GET()
        .build();
    
      final HttpResponse<String> httpResponse = HttpClient.newBuilder()
        .build()
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());
      
      String response = httpResponse.body();
      PlantOverview plantOverview = mapper.readValue(response, PlantOverview.class);
    
      return plantOverview;

    } catch(Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("It does not exist any plantOverview");
    }
  }

  /**
   * Updates plant information with the remote API.
   *
   * @param name          The name of the plant to update.
   * @param phase         The new phase of the plant.
   * @param waterInterval The new water interval of the plant.
   * @param water         The new water status of the plant.
   * @return The updated Plant object.
   * @throws JsonProcessingException If there is an issue processing JSON data.
   * @throws IllegalArgumentException If there is an issue with building or sending the HttpRequest.
   */
  public String updatePlant(String name, String phase, String waterInterval, String water, String lastWatered, String creationDate) throws JsonProcessingException {
    //Plant plant = getPlant(name);
    
    String uri = "/greenhouse/put?";
    if (name == null){
      throw new IllegalArgumentException("You must enter the plant's name");
    }
    uri += "name=" + name;
    if (phase != null) {
      if (phase.contains("Grown")) {
        uri += "&phase=Grown%20plant";
      }
      if (phase.contains("Young")) {
        uri += "&phase=Young%20plant";
      }
      if (phase.contains("Seed")) {
        uri += "&phase=Seed";
      }
    }
    if (waterInterval != null) {
      uri += "&waterInterval=" + waterInterval;
    }
    if (water != null && water.equals("True")) {
      uri += "&water=True";
    }

    if (lastWatered != null) {
      uri += "&lastWatered=";
      String[] subString = creationDate.split(" ");
      String noSpace = subString[0] + "%20" + subString[1];
      uri += noSpace;
    }
    if (creationDate != null) {
      uri += "&creationDate=";
      String[] subString = creationDate.split(" ");
      String noSpace = subString[0] + "%20" + subString[1];
      uri += noSpace;
    }


    //String json = mapper.writeValueAsString(plant);
    try {
      HttpRequest httpRequest = HttpRequest
        .newBuilder(resolveURI(uri))
        .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
        .header(ACCEPT_HEADER,APPLICATION_JSON)
        .PUT(BodyPublishers.noBody())
        .build();

      final HttpResponse<String> httpResponse = HttpClient.newBuilder()
        .build()
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());

      String response = httpResponse.body();
      return response;

    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * Creates a new plant with the remote API.
   *
   * @param name          The name of the new plant.
   * @param phase         The phase of the new plant.
   * @param waterInterval The water interval of the new plant.
   * @return
   * @return The created Plant object.
   * @throws JsonProcessingException If there is an issue processing JSON data.
   * @throws IllegalArgumentException If there is an issue with building or sending the HttpRequest.
   */
  public Plant newPlant(String name, String phase, String waterInterval) throws JsonProcessingException {
    Plant plant = new Plant(name, phase, Integer.parseInt(waterInterval));
    String json = mapper.writeValueAsString(plant);

    String uri = "/greenhouse/post?";
    uri += "name=" + name;
    if (phase.contains("Grown")) {
      uri += "&phase=Grown%20plant";
    }
    if (phase.contains("Young")) {
      uri += "&phase=Young%20plant";
    }
    if (phase.contains("Seed")) {
      uri += "&phase=Seed";
    }
    uri += "&waterInterval=" + waterInterval;


    try {
      HttpRequest httpRequest = HttpRequest
        .newBuilder(resolveURI(uri))
        .header(ACCEPT_HEADER,APPLICATION_JSON)
        .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
        .POST(BodyPublishers.ofString(json))
        .build();

      final HttpResponse<String> httpResponse = HttpClient.newBuilder()
        .build()
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());
      

      String response = httpResponse.body();
      return mapper.readValue(response, Plant.class);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Deletes a plant with the remote API by name.
   *
   * @param name The name of the plant to delete.
   * @return True if the deletion was successful, false otherwise.
   * @throws JsonProcessingException If there is an issue processing JSON data.
   * @throws IllegalArgumentException If there is an issue with building or sending the HttpRequest.
   */
  public boolean deletePlant(String name) throws JsonProcessingException {
    String uri = "/greenhouse/delete?";
    uri += "name=" + name;

    try {
      HttpRequest httpRequest = HttpRequest
        .newBuilder(resolveURI(uri))
        .header(ACCEPT_HEADER,APPLICATION_JSON)
        .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
        .DELETE()
        .build();

      final HttpResponse<String> httpResponse = HttpClient.newBuilder()
        .build()
        .send(httpRequest, HttpResponse.BodyHandlers.ofString());
        
      String response = httpResponse.body();
      return Boolean.parseBoolean(response);

    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }
}