package com.router.Model;

import java.io.Serializable;
import java.util.List;

public class ChatResultModel implements Serializable {
    /**
     * {"requestId": "c4d0fd76-3cfc-9f44-aed5-67f1ffa90a92",
     * "usage": {
     * "inputTokens": 722,
     * "outputTokens": 358
     * },
     * "output": {
     * "text": null,
     * "finishReason": null,
     * "choices": [
     * {
     * "finishReason": "stop",
     * "message": {
     * "role": "assistant",
     * "content": ""
     * }
     * }
     * ]
     * }
     * }
     * }
     */
    String requestId;
    ChatUsage usage;
    ChatOutput output;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ChatUsage getUsage() {
        return usage;
    }

    public void setUsage(ChatUsage usage) {
        this.usage = usage;
    }

    public ChatOutput getOutput() {
        return output;
    }

    public void setOutput(ChatOutput output) {
        this.output = output;
    }

    public class ChatOutput {
        String text;
        String finishReason;
        List<ChatChoices> choices;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public List<ChatChoices> getChoices() {
            return choices;
        }

        public void setChoices(List<ChatChoices> choices) {
            this.choices = choices;
        }
    }

    public class ChatChoices {
        String finishReason;
        ChatMessage message;

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public ChatMessage getMessage() {
            return message;
        }

        public void setMessage(ChatMessage message) {
            this.message = message;
        }
    }

    public class ChatMessage {
        String role;
        String content;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class ChatUsage {
        int inputTokens;
        int outputTokens;

        public int getInputTokens() {
            return inputTokens;
        }

        public void setInputTokens(int inputTokens) {
            this.inputTokens = inputTokens;
        }

        public int getOutputTokens() {
            return outputTokens;
        }

        public void setOutputTokens(int outputTokens) {
            this.outputTokens = outputTokens;
        }
    }
}
