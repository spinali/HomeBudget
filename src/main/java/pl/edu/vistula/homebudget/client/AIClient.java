package pl.edu.vistula.homebudget.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.edu.vistula.homebudget.model.Expense;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AIClient {
    private static final Logger logger = LoggerFactory.getLogger(AIClient.class);

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String endpointUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent";

    public AIClient(RestTemplate restTemplate, @Value("${gemini.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public List<String> generateCategories(List<Expense> expenses) {
        String expensesString = expenses.stream().map(Expense::toString).collect(Collectors.joining(","));
        // HTTP headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Request body
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", "Generate budget categories for those expenses"+expensesString)
                        ))
                )
        );

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send POST request to the API
        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                endpointUrl + "?key=" + apiKey, // API key in the URL
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        // Log the response
        Map<String, Object> responseBody = responseEntity.getBody();
        logger.info("API Response: {}", responseBody);

        // Handle errors
        if (responseBody.containsKey("error")) {
            logger.error("API Error: {}", responseBody.get("error"));
            throw new RuntimeException("API Error: " + responseBody.get("error"));
        }

        // Parse the response
        List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            logger.warn("No candidates found in the API response.");
            return List.of();
        }

        // Extract the generated text
        return candidates.stream()
                .map(candidate -> {
                    Map<String, Object> content = (Map<String, Object>) candidate.get("content");
                    if (content == null) {
                        return null;
                    }
                    List<Map<String, String>> parts = (List<Map<String, String>>) content.get("parts");
                    if (parts == null || parts.isEmpty()) {
                        return null;
                    }
                    return parts.get(0).get("text"); // Assuming the text is in the first part
                })
                .toList();
    }
}