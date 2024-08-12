package com.bigshare.config.jwt;

import com.bigshare.model.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Configuration
public class JWTTokenHelper {
    @Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    @Value("${jwt.auth.expires_in}")
    private long expiresIn;

    private final JwtParser parser;

    public JWTTokenHelper(@Value("${jwt.auth.secret_key}") String secretKey) {
        parser = Jwts.parser().setSigningKey(secretKey);
    }

    public static final String BEARER = "Bearer ";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    public Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String generateToken(Authentication authentication, String tokenId) {

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            if (tokenId == null) {
                tokenId = user.getId() + "-" + System.currentTimeMillis();
            }
        }

        return Jwts.builder()
                .setIssuer(appName)
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setId(tokenId)
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, secretKey)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (
                username != null &&
                        username.equals(userDetails.getUsername()) &&
                        !isTokenExpired(token)
        );
    }

    public boolean isTokenExpired(String token) {
        Date expireDate=getExpirationDate(token);
        return expireDate.before(new Date());
    }


    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }

    public String getToken( HttpServletRequest request ) {

        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader( HttpServletRequest request ) {
        return request.getHeader("Authorization");
    }


    private String toJwt(String token) {
        return token.substring(BEARER.length());
    }


    public Claims getJwtBody(String token) {
        if (StringUtils.isEmpty(token) || !token.startsWith(BEARER)) {
            throw new UsernameNotFoundException("");
        }
        String jwt = toJwt(token);
        return parser.parseClaimsJws(jwt).getBody();
    }
}
