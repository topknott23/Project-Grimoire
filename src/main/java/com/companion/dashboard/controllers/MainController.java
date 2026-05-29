package com.companion.dashboard.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    public void initialize() {

        setView("/com/companion/dashboard/fxml/GuideView.fxml");    }

    @FXML
    private void handleShowGuide() {
        setView("/com/companion/dashboard/fxml/GuideView.fxml");    }

    @FXML
    private void handleShowOptimizer() {
        setView("/com/companion/dashboard/fxml/OptimizerView.fxml");    }


    private void setView(String fxmlPath) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            System.err.println("Error: Could not load view container " + fxmlPath);
            e.printStackTrace();
        }
    }
}