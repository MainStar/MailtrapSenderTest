package org.example.converter;

import com.google.gson.JsonObject;
import org.example.dto.Email;

public interface JsonObjectConverter {

    JsonObject convertEmailToJsonObject(Email email);

}
