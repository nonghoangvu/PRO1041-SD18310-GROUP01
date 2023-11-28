/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package udpm.serviceDelivery;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author PHONG PC
 */
public class MailSender {
     public Properties mailProperties() {
        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", "phongvvutuan@gmail.com");
        props.setProperty("mail.smtp.password", "pnok mxsy totc labb");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.auth", "true");

        return props;
    }

    public String sendMail(String from, String to, String subject, String msgBody) {
        Properties props = mailProperties();
        Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        "phongvvutuan@gmail.com", "pnok mxsy totc labb");// Specify the Username and the PassWord
            }
        });

        mailSession.setDebug(false);

        try {
            Transport transport = mailSession.getTransport();
            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(subject);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, to);
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(msgBody, "text/html");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            return "SUCCESS";
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            return "INVALID_EMAIL";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

}
