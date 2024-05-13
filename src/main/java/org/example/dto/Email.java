package org.example.dto;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Email {
    private final String senderName;
    private final String senderEmail;
    private final String recipientName;
    private final String recipientEmail;
    private final String subject;
    private final String text;
    private final String html;
    private final List<File> attachments;

    private Email(Builder builder) {
        this.senderName = builder.senderName;
        this.senderEmail = builder.senderEmail;
        this.recipientName = builder.recipientName;
        this.recipientEmail = builder.recipientEmail;
        this.subject = builder.subject;
        this.text = builder.text;
        this.html = builder.html;
        this.attachments = builder.attachments;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public String getHtml() {
        return html;
    }

    public List<File> getAttachments() {
        return attachments;
    }

    public static class Builder {
        private final String senderName;
        private final String senderEmail;
        private final String recipientName;
        private final String recipientEmail;
        private final String subject;
        private final String text;
        private String html = "";
        private List<File> attachments;

        public Builder(String senderName, String senderEmail, String recipientName, String recipientEmail,
                       String text, String subject) {
            this.senderName = senderName;
            this.senderEmail = senderEmail;
            this.recipientName = recipientName;
            this.recipientEmail = recipientEmail;
            this.text = text;
            this.subject = subject;
        }

        public Builder html(String html) {
            this.html = html;
            return this;
        }

        public Builder attachments(List<File> attachments) {
            this.attachments = attachments;
            return this;
        }

        public Email build() {
            if (senderName == null || senderName.isEmpty() ||
                    senderEmail == null || senderEmail.isEmpty() ||
                    recipientName == null || recipientName.isEmpty() ||
                    recipientEmail == null || recipientEmail.isEmpty() ||
                    text == null || text.isEmpty()) {
                throw new IllegalStateException("Sender name, sender email, recipient name, " +
                        "recipient email, and text are mandatory fields.");
            }
            return new Email(this);
        }
    }
}
