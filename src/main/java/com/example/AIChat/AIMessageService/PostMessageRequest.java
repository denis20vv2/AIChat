package com.example.AIChat.AIMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Service
public class PostMessageRequest {

    String messageToAI = "{\n" +
            "  \"message\": {\n" +
            "    \"id\": \"001\",\n" +
            "    \"message\": \"Helloy\",\n" +
            "    \"userId\": \"user123\",\n" +
            "    \"groupId\": \"group123\",\n" +
            "    \"messageType\": \"user\",\n" +
            "    \"AiRepliedId\": null,\n" +
            "    \"created\": 1698175200,\n" +
            "    \"user\": {\n" +
            "      \"id\": \"user123\",\n" +
            "      \"name\": \"Ебланчик\",\n" +
            "      \"avatar\": \"avatar1\"\n" +
            "    }\n" +
            "  }\n" +
            "}\n";


    private final RestTemplate restTemplate;

    private static final String AI_API_URL = "http://192.168.1.120:8000/api/generate";

    @Autowired
    public PostMessageRequest(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String sendMessageToAI() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(messageToAI, headers);

        return restTemplate.postForObject(AI_API_URL, request, String.class);
    }
}






