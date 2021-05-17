package social_media.vk.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import social_media.vk.model.MailVerificationToken;
import social_media.vk.model.NotificationEmail;
import social_media.vk.model.User;
import social_media.vk.repository.MailVerificationTokenRepository;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final MailVerificationTokenRepository mailVerificationTokenRepository;


    public String generateToken(User user) {
        String token = UUID.randomUUID().toString();

        MailVerificationToken mailVerificationToken = new MailVerificationToken();
        mailVerificationToken.setToken(token);
        mailVerificationToken.setUser(user);
        mailVerificationTokenRepository.save(mailVerificationToken);

        return token;
    }

    public void send(NotificationEmail notificationEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("vk@info.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };

        try {
            mailSender.send(mimeMessagePreparator);
        } catch (MailException e) {
            throw new IllegalStateException("Mail Exception occurred when sending activate link");
        }
    }
}
