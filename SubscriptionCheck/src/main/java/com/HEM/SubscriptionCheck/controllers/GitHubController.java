package com.HEM.SubscriptionCheck.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/github")

public class GitHubController {

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/Mylogin")
    public ResponseEntity<String> loginWithGitHub() {
        String authUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=user:follow";
        return ResponseEntity.ok(authUrl);
    }
    @GetMapping
    public ResponseEntity<String> check() {
//        String authUrl = "https://github.com/login/oauth/authorize?client_id=" + clientId + "&redirect_uri=" + redirectUri + "&scope=user:follow";
        return ResponseEntity.ok("hello");
    }
    @GetMapping("/callback")
    public ResponseEntity<String> handleGitHubCallback(@RequestParam String code) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Exchange code for access token
        RequestBody requestBody = new FormBody.Builder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .add("code", code)
                .add("redirect_uri", redirectUri)
                .build();

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        // Extract the access token
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String accessToken = jsonNode.get("access_token").asText();
//        String accessToken="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";

        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/check-follow")
    public ResponseEntity<Boolean> checkIfUserFollows(@RequestParam String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.github.com/user/following/bytemait")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        Response response = client.newCall(request).execute();
        return ResponseEntity.ok(response.code() == 204);
    }
}
