package com.companion.dashboard.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneLoader {

    private static Stage primaryStage;
    private static Scene currentScene;


    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }


    public static void loadScene(String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(SceneLoader.class.getResource(fxmlPath));
            if (currentScene == null) {
                currentScene = new Scene(root, 1080, 720);
                primaryStage.setScene(currentScene);
            } else {

                currentScene.setRoot(root);
            }
        } catch (IOException e) {
            System.err.println("Critical Error: Could not load the FXML file at " + fxmlPath);
            e.printStackTrace();
        }
    }
}