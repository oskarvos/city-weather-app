package com.oskarvos.cityweatherapp.gemini.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiRequestDto {

    private List<Content> contents;
    private GenerationConfig generationConfig;

    @Getter
    @Setter
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @Setter
    public static class Part {
        private String text;
    }

    @Getter
    @Setter
    public static class GenerationConfig {
        private ThinkingConfig thinkingConfig;
        private Integer maxOutputTokens;
        private Double temperature;

        @Getter
        @Setter
        public static class ThinkingConfig {
            private Integer thinkingBudget;

            public void setThinkingBudget(Integer thinkingBudget) {
                this.thinkingBudget = thinkingBudget;
            }
        }
    }

}
