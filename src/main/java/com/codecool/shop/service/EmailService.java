package com.codecool.shop.service;

import com.codecool.shop.model.Order;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.Properties;

public class EmailService implements MessageService{
    @Override
    public void sendConfirmation(String orderNumber, String incomingMessage, String incomingEmail) throws MessagingException {
        // email logic
        System.out.println("SENDING EMAIL: ");
//        System.out.println("order nr: " + orderNumber);
//        System.out.println("message: " + incomingMessage);
//        System.out.println("to: " + incomingEmail);


    }

    private void firstAttempt() throws MessagingException {
        System.out.println("=== configuration success");
        // configuration
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.host", "smtp.gmail.com");

        prop.put("mail.smtp.port", "25");
//        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        String username = "darek";
        String password = "pass123";


        System.out.println("=== creating session success");
        // creating session
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        System.out.println("=== sending email success");
        // mimemessage for sending
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("darek200180@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse("darek200180@gmail.com"));
        message.setSubject("Mail Subject: TRY TO SEND EMAIL");

        String msg = "This is my first email using JavaMailer ======= I AM TRYING TO SEND EMAIL";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);

        message.setContent(multipart);

        Transport.send(message);
    }
}
