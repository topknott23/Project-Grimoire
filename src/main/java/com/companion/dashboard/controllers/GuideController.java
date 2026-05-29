package com.companion.dashboard.controllers;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class GuideController {

    @FXML
    private TextArea txtOutput;
    @FXML
    private TextField txtInput;
    @FXML
    private Button btnSend;

    @FXML
    private void handleSendQuery() {
        String query = txtInput.getText().trim();
        if (query.isEmpty()) return;

        // Clear input and show a temporary loading status
        txtInput.clear();
        txtOutput.setText("Consulting the grimoire pages for: \"" + query + "\"...\n\n(Waiting for cloud alignment...)");
        btnSend.setDisable(true);

        // Define the background task to fetch AI answers without freezing the app
        Task<String> searchTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                // TODO: Replace this placeholder with the actual GeminiService call next!
                Thread.sleep(2000); // Simulate network latency
                return "Grimoire Simulation Response:\n\nTo optimize your query regarding '" + query + "', you should focus on maximizing resource throughput using automated containment grids.";
            }
        };

        // When the background thread successfully finishes
        searchTask.setOnSucceeded(e -> {
            txtOutput.setText(searchTask.getValue());
            btnSend.setDisable(false);
        });

        // When the background thread encounters an error
        searchTask.setOnFailed(e -> {
            txtOutput.setText("Error: The cosmic links failed to gather your knowledge. Check your connection.");
            btnSend.setDisable(false);
        });

        // Fire off the background thread
        new Thread(searchTask).start();
    }
}