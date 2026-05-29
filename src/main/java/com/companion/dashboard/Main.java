package com.companion.dashboard;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.companion.dashboard.utils.SceneLoader;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Project Grimoire");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        SceneLoader.setPrimaryStage(primaryStage);

        SceneLoader.loadScene("/com/companion/dashboard/fxml/MainDashboard.fxml");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}