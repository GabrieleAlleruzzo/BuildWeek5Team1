package epicode.BW5T1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.epicode.U5W3D5.exception.NonTrovatoException;
import it.epicode.U5W3D5.model.Utente;
import it.epicode.U5W3D5.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {

    @Value("${jwt.duration}")
    private long duration;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    private UtenteService userService;

    public String createToken(Utente utente){
        return Jwts.builder().issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+duration)).
                subject(utente.getId()+"").
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).
                compact();
    }

    public void validateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                build().parse(token);
    }

    public Utente getUtenteFromToken(String token) throws NonTrovatoException {
        int id = Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                build().parseSignedClaims(token).getPayload().getSubject());

        return userService.getUtente(id);
    }
}
