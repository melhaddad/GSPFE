package ma.ilem.inventorymanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final long EXPIRATION_TIME = 60 * 60 * 24 * 10; // 10 days
    private static final String TOKEN_SECRET = "inventory_management_secret";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";
    @Autowired
    private UserService userService;

    public String getToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .claim("privileges", user.getAuthorities())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .claim("image", user.getImage())
                .compact();
    }

    public Authentication parse(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token.substring(TOKEN_PREFIX.length()))
                .getBody();

        String username = claims.getSubject();

        User user = userService.findByEmail(username);

        if (user == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(username, null, user.getAuthorities());

    }
}
