package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX RemoteApp.
 */
public class RemoteApp extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("RemoteFrontPage.fxml"));
    Parent parent = fxmlLoader.load();
    stage.setScene(new Scene(parent));
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
