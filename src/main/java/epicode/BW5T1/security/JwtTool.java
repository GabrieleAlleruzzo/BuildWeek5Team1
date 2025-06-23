package epicode.BW5T1.security;

import epicode.BW5T1.exception.NotFoundException;
import epicode.BW5T1.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
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
    private UserService userService;

    public String createToken(User user){
        return Jwts.builder().issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis()+duration)).
                subject(user.getId()+"").
                signWith(Keys.hmacShaKeyFor(secret.getBytes())).
                compact();
    }

    public void validateToken(String token){
        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                build().parse(token);
    }

    public User getUtenteFromToken(String token) throws NotFoundException {
        int id = Integer.parseInt(Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).
                build().parseSignedClaims(token).getPayload().getSubject());

        return userService.getUser(id);
    }
}
