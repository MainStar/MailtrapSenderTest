package org.example.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import org.example.converter.JsonObjectConverter;
import org.example.converter.JsonObjectConverterService;
import org.example.dto.Email;

import java.io.IOException;

import static org.example.util.Constants.MAILTRAP_API_URL;
import static org.example.util.Constants.AUTHORIZATION_HEADER_NAME;
import static org.example.util.Constants.BEARER_VALUE_NAME;
import static org.example.util.Constants.AUTHOR_API_TOKEN;
import static org.example.util.Constants.CONTENT_TYPE_HEADER_NAME;
import static org.example.util.Constants.APPLICATION_JSON_HEADER_VALUE;
import static org.example.util.Constants.UNEXPECTED_CODE_ERROR;

public class EmailClientService implements EmailClient {

    private final JsonObjectConverter jsonObjectConverter = new JsonObjectConverterService();
    private final OkHttpClient client;

    public EmailClientService (OkHttpClient client) {
        this.client = client;
    }

    @Override
    public void send(Email email) {

        JsonObject jsonBody = jsonObjectConverter.convertEmailToJsonObject(email);
        String jsonString = new Gson().toJson(jsonBody);
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_HEADER_VALUE), jsonString);

        Request request = new Request.Builder()
                .url(MAILTRAP_API_URL)
                .method( "POST", requestBody)
                .addHeader(AUTHORIZATION_HEADER_NAME, BEARER_VALUE_NAME + AUTHOR_API_TOKEN)
                .addHeader(CONTENT_TYPE_HEADER_NAME, APPLICATION_JSON_HEADER_VALUE)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException(UNEXPECTED_CODE_ERROR + response);
            }
            System.out.println("Email sent successfully!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
