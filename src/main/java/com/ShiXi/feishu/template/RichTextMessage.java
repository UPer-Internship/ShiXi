package com.ShiXi.feishu.template;

import java.util.ArrayList;
import java.util.List;

public class RichTextMessage extends FeishuMessage {
    private RichTextContent content;

    public RichTextMessage() {
        super("post");
        this.content = new RichTextContent();
    }

    public RichTextContent getContent() {
        return content;
    }

    public void addContent(String text) {
        this.content.addContent(text);
    }

    public void addContent(String text, boolean isBold) {
        this.content.addContent(text, isBold);
    }

    public static class RichTextContent {
        private PostContent post;

        public RichTextContent() {
            this.post = new PostContent();
        }

        public PostContent getZh_cn() {
            return post;
        }

        public void addContent(String text) {
            addContent(text, false);
        }

        public void addContent(String text, boolean isBold) {
            List<Object> elements = new ArrayList<>();
            elements.add(new TextElement(isBold ? "bold" : "text", text));
            post.getContent().add(new ContentItem(elements));
        }
    }

    public static class PostContent {
        private String title;
        private List<ContentItem> content = new ArrayList<>();

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<ContentItem> getContent() {
            return content;
        }
    }

    public static class ContentItem {
        private List<Object> elements;

        public ContentItem(List<Object> elements) {
            this.elements = elements;
        }

        public List<Object> getElements() {
            return elements;
        }
    }

    public static class TextElement {
        private String tag;
        private String text;

        public TextElement(String tag, String text) {
            this.tag = tag;
            this.text = text;
        }

        public String getTag() {
            return tag;
        }

        public String getText() {
            return text;
        }
    }
}
