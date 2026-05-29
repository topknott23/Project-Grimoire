package com.companion.dashboard.controllers;

import com.companion.dashboard.services.GeminiService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

public class LoreController {

    @FXML private TextField txtTheme;
    @FXML private Button btnGenerate;
    @FXML private Label lblCityName;
    @FXML private Label lblBackstory;
    @FXML private VBox npcContainer;

    @FXML
    private void handleGenerate() {
        String theme = txtTheme.getText().trim();
        if (theme.isEmpty()) return;

        // clearing the old junk out
        lblCityName.setText("Forging world data...");
        lblBackstory.setText("Awaiting JSON payload from the cloud...");
        npcContainer.getChildren().clear();
        btnGenerate.setDisable(true);

        Task<String> generateTask = new Task<>() {
            @Override
            protected String call() throws Exception {
                GeminiService aiService = new GeminiService();

                // beg the ai to give me actual json and not a conversational essay
                String prompt = "You are a world-building API. The user wants a fantasy or sci-fi world based on the theme: '" + theme + "'. " +
                        "You MUST respond with ONLY raw, valid JSON. Do not use markdown formatting or code blocks. " +
                        "Your JSON must perfectly match this exact structure: " +
                        "{\"cityName\": \"Name of the place\", \"backstory\": \"A compelling 3-sentence history.\", " +
                        "\"npcs\": [{\"name\": \"NPC 1\", \"role\": \"short description\"}, {\"name\": \"NPC 2\", \"role\": \"short description\"}]}";

                return aiService.generateResponse(prompt);
            }
        };

        generateTask.setOnSucceeded(e -> {
            try {
                String rawResponse = generateTask.getValue();

                // bruh of course it put markdown backticks anyway. stripping them...
                if (rawResponse.startsWith("```json")) {
                    rawResponse = rawResponse.replace("```json", "").replace("```", "").trim();
                }


                JSONObject data = new JSONObject(rawResponse);

                // dumping the data to the ui
                lblCityName.setText("Location: " + data.getString("cityName"));
                lblBackstory.setText(data.getString("backstory"));

                // making a bunch of cards for each npc
                JSONArray npcs = data.getJSONArray("npcs");
                for (int i = 0; i < npcs.length(); i++) {
                    JSONObject npc = npcs.getJSONObject(i);

                    Label npcLabel = new Label("👤 " + npc.getString("name") + " - " + npc.getString("role"));
                    npcLabel.setStyle("-fx-text-fill: #34d399; -fx-font-size: 14px; -fx-background-color: #27272a; -fx-padding: 10; -fx-background-radius: 5;");
                    npcLabel.setWrapText(true);
                    npcLabel.setMaxWidth(Double.MAX_VALUE);

                    npcContainer.getChildren().add(npcLabel);
                }

            } catch (Exception ex) {
                lblCityName.setText("Data Parsing Error");
                lblBackstory.setText("Failed to parse the JSON payload. See console for details.");
                ex.printStackTrace();
            } finally {
                btnGenerate.setDisable(false);
            }
        });

        generateTask.setOnFailed(e -> {
            generateTask.getException().printStackTrace();
            lblCityName.setText("Network Error");
            lblBackstory.setText("The data transmission failed.");
            btnGenerate.setDisable(false);
        });

        new Thread(generateTask).start();
    }
}