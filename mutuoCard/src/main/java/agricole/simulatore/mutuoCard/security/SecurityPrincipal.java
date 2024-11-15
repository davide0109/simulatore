package agricole.simulatore.mutuoCard.security;

import agricole.simulatore.mutuoCard.security.jwt.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import lombok.*;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

import static io.jsonwebtoken.Claims.EXPIRATION;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class SecurityPrincipal implements Serializable {

    public static final String ID_PREVENTIVO = "idSimulazione";

    public static final String DOMINIO = "dominio";

    public static final String MATRICOLA = "matricola";

    public static final String AUTHORITIES_CLAIM = "profiloSimulatore";

    private JwtConfig jwtConfig;

    private String token;

    public String getUserType() {
        return getJwtClaim(AUTHORITIES_CLAIM, String.class);
    }

    public String getIdPreventivo() {
        return getJwtClaim(ID_PREVENTIVO, String.class);
    }

    public String getMatricola() {
        return getJwtClaim(MATRICOLA, String.class);
    }

    public String getDominio() {
        return getJwtClaim(DOMINIO, String.class);
    }

    public Date getExpiration() {
        return getJwtClaim(EXPIRATION, Date.class);
    }

    private <T> T getJwtClaim(String claim, Class<T> claimType) {
        return parseJwt().getBody().get(claim, claimType);
    }

    private Jws<Claims> parseJwt() {
        return Jwts
            .parserBuilder()
            .setSigningKey(jwtConfig.getKey())
            .build()
            .parseClaimsJws(token);
    }
}