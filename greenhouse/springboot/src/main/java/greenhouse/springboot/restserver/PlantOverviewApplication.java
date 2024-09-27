package greenhouse.springboot.restserver;

import com.fasterxml.jackson.databind.Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import core.PlantOverview;
import json.PlantPersistence;

/**
 * The Spring Boot application class This class serves as the entry point for the application, 
 * configuring and starting the Spring Boot application.
 */
@SpringBootApplication
public class PlantOverviewApplication {

  /**
   * Creates and configures a Jackson Module for JSON object mapping and serialization.
   *
   * @return A Jackson Module for JSON object mapping.
   */
  @Bean
  public Module objectMapperModule() {
    return PlantPersistence.createJacksonModule();
  }

  /**
   * Creates and configures the PlantOverview bean, representing a collection of plant data.
   *
   * @return A PlantOverview bean for managing plant information.
   */
  @Bean
  public PlantOverview plantOverview() {
    return new PlantOverview();
  }
  
  /**
   * The main entry point of the application. It starts the Spring Boot application.
   *
   * @param args The command-line arguments passed to the application.
   */
  public static void main(String[] args) {
    SpringApplication.run(PlantOverviewApplication.class, args);
  }
}
