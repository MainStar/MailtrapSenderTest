package org.example.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.example.dto.Email;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.example.util.Constants.EMAIL_FIELD_NAME;
import static org.example.util.Constants.NAME_FIELD_NAME;
import static org.example.util.Constants.FROM_FIELD_NAME;
import static org.example.util.Constants.TO_FIELD_NAME;
import static org.example.util.Constants.SUBJECT_FIELD_NAME;
import static org.example.util.Constants.TEXT_FIELD_NAME;
import static org.example.util.Constants.CATEGORY_FIELD_NAME;
import static org.example.util.Constants.CONTENT_FIELD_NAME;
import static org.example.util.Constants.TYPE_FIELD_NAME;
import static org.example.util.Constants.FILENAME_FIELD_NAME;
import static org.example.util.Constants.ATTACHMENTS_FIELD_NAME;

public class JsonObjectConverterService implements JsonObjectConverter {
    @Override
    public JsonObject convertEmailToJsonObject(Email email) {
        // Create JsonArray for field "from"
        JsonObject from = new JsonObject();
        from.addProperty(EMAIL_FIELD_NAME, email.getSenderEmail());
        from.addProperty(NAME_FIELD_NAME, email.getSenderName());

        // Create JsonArray for field "to"
        JsonArray to = new JsonArray();
        JsonObject recipient = new JsonObject();
        recipient.addProperty(EMAIL_FIELD_NAME, email.getRecipientEmail());
        recipient.addProperty(NAME_FIELD_NAME, email.getRecipientName());
        to.add(recipient);

        // Create a parent JsonObject
        JsonObject jsonBody = new JsonObject();
        jsonBody.add(FROM_FIELD_NAME, from);
        jsonBody.add(TO_FIELD_NAME, to);
        jsonBody.addProperty(SUBJECT_FIELD_NAME, email.getSubject());
        jsonBody.addProperty(TEXT_FIELD_NAME, email.getText());
        jsonBody.addProperty(CATEGORY_FIELD_NAME, "Integration Test");

        if (email.getAttachments() == null) return jsonBody;

        JsonArray attachments = new JsonArray();
        for (File attachment : email.getAttachments()) {
            JsonObject attachmentObject = new JsonObject();
            try {
                byte[] attachmentBytes = Files.readAllBytes(attachment.toPath());
                String attachmentContent = Base64.encodeBase64String(attachmentBytes);
                attachmentObject.addProperty(CONTENT_FIELD_NAME, attachmentContent);
                attachmentObject.addProperty(TYPE_FIELD_NAME, "application/octet-stream");
                attachmentObject.addProperty(FILENAME_FIELD_NAME, attachment.getName());
                attachments.add(attachmentObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        jsonBody.add(ATTACHMENTS_FIELD_NAME, attachments);

        return jsonBody;
    }
}
