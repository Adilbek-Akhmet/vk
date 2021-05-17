package social_media.vk.config;

import com.google.common.net.HttpHeaders;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "security.jwt")
public class JwtConfig {

    private String prefix;
    private Long expiration;
    private String secretKey;

    public String getAuthorization() {
        return HttpHeaders.AUTHORIZATION;
    }
}
