package social_media.vk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import social_media.vk.model.MailVerificationToken;

import java.util.Optional;

public interface MailVerificationTokenRepository extends JpaRepository<MailVerificationToken, Long> {
    Optional<MailVerificationToken> findMailVerificationTokenByToken(String token);
}
