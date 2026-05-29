package com.companion.dashboard.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.io.IOException;

public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML private HBox titleBar;
    @FXML private Button btnClose;
    @FXML private Button btnMinimize;
    
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void initialize() {
        if (titleBar != null) {
            titleBar.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            titleBar.setOnMouseDragged(event -> {
                Stage stage = (Stage) titleBar.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
        }
        setView("/com/companion/dashboard/fxml/GuideView.fxml");    }

    @FXML
    private void handleShowGuide() {
        setView("/com/companion/dashboard/fxml/GuideView.fxml");    }

    @FXML
    private void handleShowOptimizer() {
        setView("/com/companion/dashboard/fxml/OptimizerView.fxml");    }

    @FXML
    private void handleMinimize() {
        Stage stage = (Stage) contentArea.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleClose() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void handleShowLore() {
        setView("/com/companion/dashboard/fxml/LoreView.fxml");
    }


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