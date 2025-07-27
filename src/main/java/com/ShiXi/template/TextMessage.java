package com.ShiXi.template;

public class TextMessage extends FeishuMessage {
    private TextContent content;

    public TextMessage(String text) {
        super("text");
        this.content = new TextContent(text);
    }

    public TextContent getContent() {
        return content;
    }

    public void setContent(TextContent content) {
        this.content = content;
    }

    public static class TextContent {
        private String text;

        public TextContent(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
