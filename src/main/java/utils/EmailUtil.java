package utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

public class EmailUtil {

    public static void sendEmailWithAttachments(String subject, String messageBody, String recipientEmail, String... attachmentPaths) throws Exception {
        // Create the email message
        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator("yescust.onbord01@gmail.com", "yesbank!123"));
        email.setStartTLSEnabled(true);
        email.setFrom("yescust.onbord01@gmail.com");
        email.setSubject(subject);
        email.setMsg(messageBody);
        email.addTo(recipientEmail);

        // Add attachments
        for (String attachmentPath : attachmentPaths) {
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(attachmentPath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            email.attach(attachment);
        }

        // Send the email
        email.send();
    }
}
