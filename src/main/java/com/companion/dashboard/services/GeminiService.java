package com.companion.dashboard.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiService {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String API_KEY = dotenv.get("GEMINI_API_KEY");;
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + API_KEY;

    public String generateResponse(String userPrompt) throws Exception {

        JSONObject textPart = new JSONObject().put("text", userPrompt);
        JSONObject part = new JSONObject().put("parts", new JSONArray().put(textPart));
        JSONObject payload = new JSONObject().put("contents", new JSONArray().put(part));

        // pray the connection works
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

        // sending it into the void
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() == 200) {
            // parsing this nested json nightmare to get the actual text
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");
        } else {
            throw new RuntimeException("API Error: " + response.statusCode() + " - " + response.body());
        }
    }
}