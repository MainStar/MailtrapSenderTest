package org.example.service;

import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.Protocol;
import okhttp3.Call;
import org.example.converter.JsonObjectConverter;
import org.example.converter.JsonObjectConverterService;
import org.example.dto.Email;
import org.example.util.EmailFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class EmailClientServiceTest {

    private static final String MAILTRAP_API_URL = "https://api.mailtrap.io";

    private EmailClientService emailClientService;
    private OkHttpClient mockClient;
    private JsonObjectConverter mockConverter;

    @Before
    public void setUp() {
        mockClient = mock(OkHttpClient.class);
        mockConverter = mock(JsonObjectConverterService.class);
        emailClientService = new EmailClientService(mockClient);
    }

    @Test
    public void should_sendEmailSuccessfullySent_ReturnsSuccessMessage() throws IOException {
        Email email = EmailFactory.createEmailDto();
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("to", email.getRecipientEmail());
        jsonBody.addProperty("subject", email.getSubject());
        jsonBody.addProperty("text", email.getText());

        Response mockResponse = new Response.Builder()
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .message("OK")
                .request(new Request.Builder().url(MAILTRAP_API_URL).build())
                .build();

        when(mockConverter.convertEmailToJsonObject(email)).thenReturn(jsonBody);

        Call mockCall = mock(Call.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);

        assertDoesNotThrow(() -> emailClientService.send(email));
    }

    @Test
    public void should_sendEmailNotSuccessfullySent_ThrowsRuntimeException() throws IOException {
        Email email = EmailFactory.createEmailDto();
        JsonObject jsonBody = new JsonObject();
        jsonBody.addProperty("to", email.getRecipientEmail());
        jsonBody.addProperty("subject", email.getSubject());
        jsonBody.addProperty("text", email.getText());

        Response mockResponse = new Response.Builder()
                .code(500)
                .protocol(Protocol.HTTP_1_1)
                .message("Internal Server Error")
                .request(new Request.Builder().url(MAILTRAP_API_URL).build())
                .build();

        when(mockConverter.convertEmailToJsonObject(email)).thenReturn(jsonBody);

        Call mockCall = mock(Call.class);
        when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
        when(mockCall.execute()).thenReturn(mockResponse);

        Assertions.assertThrows(RuntimeException.class, () -> emailClientService.send(email));
    }
}
