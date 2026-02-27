package com.oskarvos.cityweatherapp.gemini.config;

import com.oskarvos.cityweatherapp.gemini.dto.request.GeminiRequestDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Component
public class GeminiConfig {
    private final String apiKey;
    private final String apiUrl;
    private final Integer thinkingBudget;
    private final Integer maxOutputTokens;
    private final Double temperature;

    // Todo - убрать с кода ключ
    public GeminiConfig(@Value("${gemini.api.key}") String apiKey,
                        @Value("${gemini.api.url}") String apiUrl) {
        this.apiKey = apiKey;
        this.apiUrl = apiUrl;
        this.thinkingBudget = 0;
        this.maxOutputTokens = 50;
        this.temperature = 0.5;
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
