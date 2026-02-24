package com.oskarvos.cityweatherapp.gemini.dto.request;

import java.util.List;

public class GeminiRequestDto {

    private List<Content> contents;
    private GenerationConfig generationConfig;

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public GenerationConfig getGenerationConfig() {
        return generationConfig;
    }

    public void setGenerationConfig(GenerationConfig generationConfig) {
        this.generationConfig = generationConfig;
    }

    public static class Content {
        private List<Part> parts;

        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }
    }

    public static class Part {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class GenerationConfig {
        private ThinkingConfig thinkingConfig;
        private Integer maxOutputTokens;
        private Double temperature;

        public static class ThinkingConfig {
            private Integer thinkingBudget;

            public Integer getThinkingBudget() {
                return thinkingBudget;
            }

            public void setThinkingBudget(Integer thinkingBudget) {
                this.thinkingBudget = thinkingBudget;
            }
        }

        public ThinkingConfig getThinkingConfig() {
            return thinkingConfig;
        }

        public void setThinkingConfig(ThinkingConfig thinkingConfig) {
            this.thinkingConfig = thinkingConfig;
        }

        public Integer getMaxOutputTokens() {
            return maxOutputTokens;
        }

        public void setMaxOutputTokens(Integer maxOutputTokens) {
            this.maxOutputTokens = maxOutputTokens;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }
    }

}
