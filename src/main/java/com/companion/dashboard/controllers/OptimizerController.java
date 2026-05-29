package com.companion.dashboard.controllers;

import com.companion.dashboard.services.GeminiService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class OptimizerController {

    @FXML
    private ComboBox<String> ramCombo;
    @FXML
    private Slider modSlider;
    @FXML
    private Button btnOptimize;
    @FXML
    private TextArea txtOutput;

    @FXML
    private void handleOptimize() {
        String systemRam = ramCombo.getValue();
        int modCount = (int) modSlider.getValue();

        txtOutput.setText("Analyzing system architecture and calculating Garbage Collection flags...\n\nPlease wait.");
        btnOptimize.setDisable(true);

        Task<String> optimizationTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                GeminiService aiService = new GeminiService();

                String prompt = "You are a senior Java system architect. The user has a PC with " + systemRam +
                        " of total RAM and wants to play a Minecraft modpack with " + modCount +
                        " mods. Calculate the optimal exact JVM launch arguments for them. " +
                        "Provide ONLY the arguments block starting with -Xms and ending with the GC flags. " +
                        "Do not include conversational filler.";

                return aiService.generateResponse(prompt);
            }
        };

        optimizationTask.setOnSucceeded(e -> {
            txtOutput.setText(optimizationTask.getValue());
            btnOptimize.setDisable(false);
        });

        optimizationTask.setOnFailed(e -> {
            txtOutput.setText("Error: Could not calculate arguments. Check your API connection.");
            btnOptimize.setDisable(false);
        });

        new Thread(optimizationTask).start();
    }
}