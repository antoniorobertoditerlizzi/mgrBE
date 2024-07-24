package com.vigilfuoco.mgr.utility;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MailSender {

    public static void main(String[] args) {
        sendEmail("Oggetto", "Messaggio", "Email");
    }

    public static void sendEmail(String oggetto, String messaggio, String toEmail) {

        // READ PROPERTIES
        String fromEmail = PropertiesReader.getProperty("fromEmail");
        String password = PropertiesReader.getProperty("password");
        //String toEmail = PropertiesReader.getProperty("toEmail");
        String mailSmtpHost = PropertiesReader.getProperty("mailSmtpHost");
        String mailSmtpSocketFactoryPort = PropertiesReader.getProperty("mailSmtpSocketFactoryPort");
        String mailSmtpAuth = PropertiesReader.getProperty("mailSmtpAuth");
        String mailSmtpPort = PropertiesReader.getProperty("mailSmtpPort");

        // Proxy settings
        String proxyHost = PropertiesReader.getProperty("proxyHost");
        String proxyPort = PropertiesReader.getProperty("proxyPort");
        String proxyUser = PropertiesReader.getProperty("proxyUser");
        String proxyPassword = PropertiesReader.getProperty("proxyPassword");

        // Set proxy properties
        if (proxyHost != null && !proxyHost.isEmpty() && proxyPort != null && !proxyPort.isEmpty()) {
            System.setProperty("http.proxyHost", proxyHost);
            System.setProperty("http.proxyPort", proxyPort);
            System.setProperty("https.proxyHost", proxyHost);
            System.setProperty("https.proxyPort", proxyPort);

            if (proxyUser != null && !proxyUser.isEmpty() && proxyPassword != null && !proxyPassword.isEmpty()) {
                System.setProperty("http.proxyUser", proxyUser);
                System.setProperty("http.proxyPassword", proxyPassword);
                System.setProperty("https.proxyUser", proxyUser);
                System.setProperty("https.proxyPassword", proxyPassword);
            }
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", mailSmtpHost); // SMTP Host
        props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort); // SSL Port
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
        props.put("mail.smtp.auth", mailSmtpAuth); // Enabling SMTP Authentication
        props.put("mail.smtp.port", mailSmtpPort); // SMTP Port

        Authenticator auth = new Authenticator() {
            // override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);
        MailUtils.sendEmail(session, toEmail, oggetto, messaggio);
    }
}
