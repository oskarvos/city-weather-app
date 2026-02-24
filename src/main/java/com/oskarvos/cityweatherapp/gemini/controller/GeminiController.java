package com.oskarvos.cityweatherapp.gemini.controller;

import com.oskarvos.cityweatherapp.gemini.config.GeminiConfig;
import com.oskarvos.cityweatherapp.gemini.dto.request.GeminiRequestDto;
import com.oskarvos.cityweatherapp.gemini.dto.response.GeminiResponseDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class GeminiController {

    private final GeminiConfig geminiConfig;
    private final RestTemplate restTemplate;

    public GeminiController(GeminiConfig geminiConfig,
                            RestTemplate restTemplate) {
        this.geminiConfig = geminiConfig;
        this.restTemplate = restTemplate;
    }

    @PostMapping("/gemini/ask")
    public ResponseEntity<?> askGemini(@RequestBody GeminiRequestDto request) {
        try {
            if (request.getContents() == null || request.getContents().isEmpty()) {
                return ResponseEntity.badRequest().body("Отсутствует запрос Gemini");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<GeminiRequestDto> entity = new HttpEntity<>(request, headers);

            String url = geminiConfig.getApiUrl() + "?key=" + geminiConfig.getApiKey();

            ResponseEntity<GeminiResponseDto> response = restTemplate.postForEntity(url, entity, GeminiResponseDto.class);

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }

    }

}
