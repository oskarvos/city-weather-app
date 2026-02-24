package com.oskarvos.cityweatherapp.gemini.config;

import com.oskarvos.cityweatherapp.gemini.dto.request.GeminiRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeminiConfig {
    private String apiKey;
    private String apiUrl;
    private Integer thinkingBudget;
    private Integer maxOutputTokens;
    private Double temperature;

    public GeminiConfig(@Value("${gemini.api.key}") String apiKey,
                        @Value("${gemini.api.url}") String apiUrl) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.thinkingBudget = 0;
        this.maxOutputTokens = 50;
        this.temperature = 0.5;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public Integer getThinkingBudget() {
        return thinkingBudget;
    }

    public Integer getMaxOutputTokens() {
        return maxOutputTokens;
    }

    public Double getTemperature() {
        return temperature;
    }

    public GeminiRequestDto createRequest(String prompt) {
        GeminiRequestDto request = new GeminiRequestDto();

        GeminiRequestDto.Part part = new GeminiRequestDto.Part();
        part.setText(prompt);

        GeminiRequestDto.Content content = new GeminiRequestDto.Content();
        content.setParts(List.of(part));
        request.setContents(List.of(content));

        GeminiRequestDto.GenerationConfig.ThinkingConfig thinkingConfig =
                new GeminiRequestDto.GenerationConfig.ThinkingConfig();
        thinkingConfig.setThinkingBudget(thinkingBudget);

        GeminiRequestDto.GenerationConfig generationConfig = new GeminiRequestDto.GenerationConfig();
        generationConfig.setThinkingConfig(thinkingConfig);
        generationConfig.setMaxOutputTokens(maxOutputTokens);
        generationConfig.setTemperature(temperature);

        request.setGenerationConfig(generationConfig);

        return request;
    }

}
