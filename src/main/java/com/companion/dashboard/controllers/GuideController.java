package com.companion.dashboard.controllers;

import com.companion.dashboard.services.GeminiService;
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


        txtInput.clear();
        txtOutput.setText("Consulting the grimoire pages for: \"" + query + "\"...\n\n(Waiting for cloud alignment...)");
        btnSend.setDisable(true);

        // running this in the background so the ui doesn't freeze and crash
        Task<String> searchTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                // waking up the ai
                GeminiService aiService = new GeminiService();

                // feeding it some context so it doesn't give me a recipe for pancakes
                String systemPrompt = "You are a helpful gaming assistant inside a desktop app called Project Grimoire. Keep answers concise. Answer this: " + query;

                // ship it
                return aiService.generateResponse(systemPrompt);
            }
        };

        // ayy it worked
        searchTask.setOnSucceeded(e -> {
            txtOutput.setText(searchTask.getValue());
            btnSend.setDisable(false);
        });

        // rip. something went wrong
        searchTask.setOnFailed(e -> {

            searchTask.getException().printStackTrace();

            txtOutput.setText("Error: The cosmic links failed to gather your knowledge. Check your connection.");
            btnSend.setDisable(false);
        });

        new Thread(searchTask).start();
    }
}