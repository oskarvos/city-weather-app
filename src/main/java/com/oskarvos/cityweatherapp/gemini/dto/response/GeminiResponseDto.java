package com.oskarvos.cityweatherapp.gemini.dto.response;

import java.util.List;

public class GeminiResponseDto {

    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(UsageMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }

    public static class Candidate {
        private ResponseContent content;
        private String finishReason;

        public ResponseContent getContent() {
            return content;
        }

        public void setContent(ResponseContent content) {
            this.content = content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }
    }

    public static class ResponseContent {
        private List<ResponsePart> parts;

        public List<ResponsePart> getParts() {
            return parts;
        }

        public void setParts(List<ResponsePart> parts) {
            this.parts = parts;
        }
    }

    public static class ResponsePart {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class UsageMetadata {
        private Integer promptTokenCount;
        private Integer candidatesTokenCount;
        private Integer totalTokenCount;

        public Integer getPromptTokenCount() {
            return promptTokenCount;
        }

        public void setPromptTokenCount(Integer promptTokenCount) {
            this.promptTokenCount = promptTokenCount;
        }

        public Integer getCandidatesTokenCount() {
            return candidatesTokenCount;
        }

        public void setCandidatesTokenCount(Integer candidatesTokenCount) {
            this.candidatesTokenCount = candidatesTokenCount;
        }

        public Integer getTotalTokenCount() {
            return totalTokenCount;
        }

        public void setTotalTokenCount(Integer totalTokenCount) {
            this.totalTokenCount = totalTokenCount;
        }
    }

}
