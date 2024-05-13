package org.example.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.example.dto.Email;
import org.junit.Test;

import static org.example.util.EmailFactory.createEmailDto;
import static org.junit.Assert.assertEquals;

public class JsonObjectConverterServiceTest {

    private final JsonObjectConverter converter = new JsonObjectConverterService();

    @Test
    public void should_convertEmailToJsonObject() {
        Email email = createEmailDto();
        JsonObject actual = converter.convertEmailToJsonObject(email);

        JsonObject from = actual.getAsJsonObject("from");
        JsonElement to = actual.get("to");
        JsonElement subject = actual.get("subject");
        JsonElement attachments = actual.get("attachments").getAsJsonArray().get(0).getAsJsonObject().get("filename");
        JsonElement category = actual.get("category");

        assertEquals("test@demomailtrap.com", from.get("email").getAsString());
        assertEquals("Mailtrap Test", from.get("name").getAsString());
        assertEquals("Recipient Vlad", to.getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString());
        assertEquals("test@gmail.com", to.getAsJsonArray().get(0).getAsJsonObject().get("email").getAsString());
        assertEquals("Subject", subject.getAsString());
        assertEquals("test.txt", attachments.getAsString());
        assertEquals("Integration Test", category.getAsString());
    }
}
