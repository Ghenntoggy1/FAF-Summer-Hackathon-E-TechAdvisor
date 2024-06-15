package com.ETechAdvisor.ETechAdvisor.ChatGPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    @Service
    public class ChatGptService {

        @Value("${chatgpt.api.url}")
        private String apiUrl;

        @Value("${chatgpt.api.key}")
        private String apiKey;

        private final RestTemplate restTemplate;


        @Autowired
        public ChatGptService(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

        public Map<String, Double> getImportanceValues(String prompt) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = String.format(
                    "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}], \"max_tokens\": 150}",
                    prompt
            );
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            return parseResponse(response.getBody());
        }



        private Map<String, Double> parseResponse(String responseBody) {
            System.out.println("Response from API: " + responseBody);
            Map<String, Double> parameters = new LinkedHashMap<>();
            List<Double> scores = new ArrayList<>();
            // Updated regex pattern to handle extra characters and potential formatting issues
            Pattern pattern = Pattern.compile("(.+?): (\\d+\\.\\d+)");
            Matcher matcher = pattern.matcher(responseBody);

            while (matcher.find()) {
                String feature = matcher.group(1).trim();
                feature = feature.replace("\\n", "").trim();
                feature = feature.replace("\"content\": \"", "").trim();
                Double importance = Double.parseDouble(matcher.group(2));
                parameters.put(feature, importance);
                scores.add(importance); //are stored in the order mentioned in the
            }
            System.out.println(parameters);
            //call the scores method

            return parameters;
        }

    }


