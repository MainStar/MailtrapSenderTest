package org.example;

import okhttp3.OkHttpClient;
import org.example.dto.Email;
import org.example.service.EmailClient;
import org.example.service.EmailClientService;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        File file = new File("local_storage/attachments/test.txt");

        Email email = new Email.Builder("Mailtrap Test", "mailtrap@demomailtrap.com",
                "Recipient Vlad", "mainstar313@gmail.com",
                "Message text", "Subject")
                .html("<html><body><h1>Test HTML</h1></body></html>")
                .attachments(List.of(file))
                .build();

        EmailClient emailClient = new EmailClientService(client);
        emailClient.send(email);
    }
}