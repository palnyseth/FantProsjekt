/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.nyseth.fantprosjekt.auth;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 *
 * @author nyseth
 */
public class MailService {
    String mailHost = ""; //Mailserver. E.g. smtp.gmail.com
    
    String mailUser = ""; //Email. E.g. *****@gmail.com
    String mailPwd = ""; //Password for said email.
    
    
    
    public void sendMail(String reciever, String subject, String message) {
        System.out.println(reciever + " . " + subject + " . " + message);
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
            mailMsg.setFrom(new InternetAddress(mailUser));
            mailMsg.setSubject(subject);
            mailMsg.setText(message);
            
            Transport.send(mailMsg);
        } catch (MessagingException ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
}
