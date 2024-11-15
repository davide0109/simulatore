package agricole.simulatore.mutuoCard.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;

import java.security.*;
import java.util.Base64;

@Configuration
public class JwtConfig {

    @Value("${jwt.key.public}")
    private String publicKeyString;

    public Key getKey() {
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
    }
}