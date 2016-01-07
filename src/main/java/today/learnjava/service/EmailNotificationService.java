package today.learnjava.service;

import today.learnjava.model.*;
import today.learnjava.repository.AccountRepository;
import today.learnjava.util.EmailTemplateUtil;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Leonard
 * Date: 11/15/14
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class EmailNotificationService {

    @Autowired
    AccountRepository accountRepository;

    public static SendGrid sendgrid;

    public void sendAccountConfirmationMail(Account account) {
        String recipientEmail = account.getEmail();
        String subject = "Please confirm your account";
        String message = "<p>Dear wanderer,<br></br></p>" +
                "<p>Welcome in to the GuideBubble tribe! You are a few steps away from promoting your tours and " +
                "connecting with travelers around the world!<br></br>" +
                "All you have to do is click the following link and confirm your account:" +
                "<a href=\"http://www.guidebubble.com/confirmAccount/" + account.getPassword().substring(0,10) + account.getId() + "\">http://www.guidebubble.com/confirmAccount/" + account.getPassword().substring(0,10) + account.getId() + "</a><br></br><br></br></p>" +
                "<p><br></br>" +
                "We wish you endless travels!</p>";
        sendMail(recipientEmail, subject, EmailTemplateUtil.fillTemplate(subject, message, ""));
    }

    public void sendForgotPasswordMail(Account account) {
        String recipientEmail = account.getEmail();
        String subject = "Recover password";
        String message = "<p>Dear traveler,<br></br></p><br></br>" +
                "<p>Please click the following link in order to change your password!<br></br>" +
                "<a href=\"http://www.guidebubble.com/changePassword/" + account.getPassword().substring(0,35) + account.getId() + "\">http://www.guidebubble.com/changePassword/" + account.getPassword().substring(0,35) + account.getId() + "</a><br></br><br></br></p>" +
                "<p>We wish you endless travels!</p>";
        sendMail(recipientEmail, subject, EmailTemplateUtil.fillTemplate(subject, message, ""));
    }

    public static void sendMail (String recipientEmail, String subject, String message) {
        try {
            if (sendgrid == null) {
                sendgrid = new SendGrid("xxx-SendGrid-Key-xxx", "xxx-SendGrig-Value-xxx");
            }
            SendGrid.Email email = new SendGrid.Email();

            email.addTo(recipientEmail);
            email.setFrom("guides@GuideBubble.com");
            email.setSubject(subject);
            email.setHtml(message);

            SendGrid.Response response = sendgrid.send(email);

        } catch (SendGridException exception) {
            exception.printStackTrace();
        }
    }

}
