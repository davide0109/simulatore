package agricole.simulatore.mutuoCard.security.jwt;

import agricole.simulatore.mutuoCard.dto.enums.RuoliUtenteEnum;
import agricole.simulatore.mutuoCard.utils.exception.JwtValidationException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.*;

import static io.jsonwebtoken.Claims.EXPIRATION;

@Component
@Slf4j
public class JwtUtil {

  /*  @PostConstruct
    public void init() {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_CLAIM, "SIMULATORE_ADMIN");
        claims.put(ID_PREVENTIVO, null);
        claims.put(MATRICOLA, "J53632");
        claims.put(DOMINIO, "CARIPRPCCOLL");
        log.info("JWT: {}", Jwts.builder().setClaims(claims).setExpiration(Date.from(ZonedDateTime.now().plusYears(10).toInstant())).signWith(jwtConfig.getKey()).compact());
    }*/

    public static final String AUTHORITIES_CLAIM = "profiloSimulatore";

    public static final String ID_PREVENTIVO = "idSimulazione";

    public static final String DOMINIO = "dominio";

    public static final String MATRICOLA = "matricola";

    @Autowired
    private JwtConfig jwtConfig;

    public Boolean isTokenValid(String token) {
        try {
            getExpiration(token);
            if (getJwtAuthorities(token).isEmpty())
                throw new JwtValidationException("Token non valido! Profilo Simulatore non impostato.");
            if (getJwtAuthorities(token).get(0).getAuthority().equalsIgnoreCase(RuoliUtenteEnum.SIMULATORE_GESTORE.toString()) && Objects.isNull(getIdPreventivo(token)))
                throw new JwtValidationException("Token non valido! Id Simulazione non impostato.");
            if (Objects.isNull(getDominio(token)))
                throw new JwtValidationException("Token non valido! Dominio non impostato.");
            if (Objects.isNull(getMatricola(token)))
                throw new JwtValidationException("Token non valido! Matricola non impostata.");
            return Boolean.TRUE;
        } catch (JwtValidationException e) {
            log.error(e.getMessage());
            throw e;
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            throw new JwtValidationException("Token non valido! Scaduto.");
        } catch (Exception e) {
            log.error("Errore generico durante la validazione del token: {}", e.getMessage());
            throw new JwtValidationException("Errore generico durante la validazione del token.");
        }
    }

    public List<GrantedAuthority> getJwtAuthorities(String token) {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(
            getJwtClaim(token, AUTHORITIES_CLAIM, String.class)
        );
    }

    public String getIdPreventivo(String token) {
        return getJwtClaim(token, ID_PREVENTIVO, String.class);
    }

    public String getMatricola(String token) {
        return getJwtClaim(token, MATRICOLA, String.class);
    }

    public String getDominio(String token) {
        return getJwtClaim(token, DOMINIO, String.class);
    }

    public void getExpiration(String token) {
        getJwtClaim(token, EXPIRATION, Date.class);
    }

    public <T> T getJwtClaim(String token, String claim, Class<T> claimType) {
        return parseJwt(token).getBody().get(claim, claimType);
    }

    private Jws<Claims> parseJwt(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(jwtConfig.getKey())
            .build()
            .parseClaimsJws(token);
    }
}