package ma.ilem.inventorymanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.ilem.inventorymanagement.entity.User;
import ma.ilem.inventorymanagement.pojo.AccountCredentials;
import ma.ilem.inventorymanagement.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ma.ilem.inventorymanagement.util.JwtUtil.HEADER_STRING;
import static ma.ilem.inventorymanagement.util.JwtUtil.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        AccountCredentials user;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), AccountCredentials.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        User user = (User) authResult.getPrincipal();

        String token = jwtUtil.getToken(user);

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
    }
}
