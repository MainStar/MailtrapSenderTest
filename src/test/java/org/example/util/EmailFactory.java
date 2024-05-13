package org.example.util;

import org.example.dto.Email;

import java.io.File;
import java.util.List;

public class EmailFactory {

    public static Email createEmailDto() {
        File file = new File("local_storage/attachments/test.txt");
        return new Email.Builder("Mailtrap Test", "test@demomailtrap.com",
                "Recipient Vlad", "test@gmail.com",
                "Message text", "Subject")
                .html("html")
                .attachments(List.of(file))
                .build();
    }
}
