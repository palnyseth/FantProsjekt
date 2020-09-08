/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.nyseth.fantprosjekt.auth;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author nyseth
 */
public class MailService {
    String mailHost = "mailgoesheresmile";
    String mailUser = "";
    String mailPwd = "";
    
    public void sendMail(String reciever, String subject, String message) {
        
        Properties mailProp = new Properties();
        mailProp.put("mail.smtp.starttls.enable", true);
        mailProp.put("mail.smtp.auth", "true");
        mailProp.put("mail.smtp.host", mailHost);
        mailProp.put("mail.smtp.port", "25");
        
        Session mailSess = Session.getInstance(mailProp, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUser, mailPwd);
            }
        });
        
        try {
            Message mailMsg = new MimeMessage(mailSess);
            mailMsg.setRecipient(Message.RecipientType.TO, new InternetAddress(reciever));
            mailMsg.setFrom(new InternetAddress(mailHost));
            mailMsg.setSubject(subject);
            mailMsg.setText(message);
            
            Transport.send(mailMsg);
        } catch (MessagingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
    } 
}
    
}
