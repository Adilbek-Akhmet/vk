package social_media.vk.service;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social_media.vk.dto.AuthenticationResponse;
import social_media.vk.dto.LoginRequest;
import social_media.vk.dto.RegisterRequest;
import social_media.vk.model.MailVerificationToken;
import social_media.vk.model.NotificationEmail;
import social_media.vk.model.User;
import social_media.vk.repository.MailVerificationTokenRepository;
import social_media.vk.repository.UserRepository;

import java.time.Instant;

@Service
@AllArgsConstructor
public class AuthService {

    private final MailService mailService;
    private final MailVerificationTokenRepository mailVerificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public void signup(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .createdAt(Instant.now())
                .enabled(false)
                .build();

        userRepository.save(user);

        String token = mailService.generateToken(user);
        mailService.send(new NotificationEmail(
                "Activate email",
                user.getEmail(),
                "http://localhost:8080/api/auth/accountVerification/" + token
        ));
    }

    public void verifyAccount(String token) {
        MailVerificationToken mailVerificationToken = mailVerificationTokenRepository.findMailVerificationTokenByToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid token"));

        User user = mailVerificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        String token = jwtTokenProvider.generateToken(authenticate);
        User user = userRepository.findByEmail(loginRequest.getEmail()).get();
//                .orElseThrow(() -> new IllegalStateException("User not found"));
        return new AuthenticationResponse(token, user);

    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("You are not log in"));
    }
}
