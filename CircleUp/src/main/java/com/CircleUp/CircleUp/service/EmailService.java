package com.CircleUp.CircleUp.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(String toEmail, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("CircleUp — Verify Your Email");

            String link = baseUrl + "/auth/verify?token=" + token;
            String body = "<h2>Welcome to CircleUp!</h2>"
                    + "<p>Click the button below to verify your email:</p>"
                    + "<a href='" + link + "' style='background:#4CAF50;color:white;"
                    + "padding:10px 20px;text-decoration:none;border-radius:5px;'>"
                    + "Verify Email</a>"
                    + "<p>Or copy this link: " + link + "</p>"
                    + "<p>This link expires in 24 hours.</p>";

            helper.setText(body, true); // true = HTML

            mailSender.send(message);
            System.out.println("Verification email sent to: " + toEmail);

        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}