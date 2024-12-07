package org.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("image-management-view.fxml"));
            Parent root = loader.load();

            // Set title
            primaryStage.setTitle("Image Management Tool");

            // Create scene
            Scene scene = new Scene(root, 800, 600);

            // (Optional) Attach stylesheets if any
            // scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            // Set scene
            primaryStage.setScene(scene);

            // Disable resizing (optional)
            primaryStage.setResizable(false);

            // Show the stage
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load FXML file.");
        }
    }
}
