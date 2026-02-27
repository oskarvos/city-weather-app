package com.oskarvos.cityweatherapp.gemini.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GeminiResponseDto {

    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;

    @Getter
    @Setter
    public static class Candidate {
        private ResponseContent content;
        private String finishReason;
    }

    @Getter
    @Setter
    public static class ResponseContent {
        private List<ResponsePart> parts;
    }

    @Getter
    @Setter
    public static class ResponsePart {
        private String text;
    }

    @Getter
    @Setter
    public static class UsageMetadata {
        private Integer promptTokenCount;
        private Integer candidatesTokenCount;
        private Integer totalTokenCount;
    }

}
