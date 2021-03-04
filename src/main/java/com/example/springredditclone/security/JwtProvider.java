package com.example.springredditclone.security;

import com.example.springredditclone.exceptions.SpringRedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
import static java.util.Date.from;

/**
 * Class: JwtProvider security
 * We are using AsymmetricEncryption to sign our JWT’s using Java Keystore (using Public-Private Key)
 */
@Service
public class JwtProvider {

    private KeyStore keyStore;
    /**
     * So the first thing you can observe is we have introduced the field jwtExpirationInMillis
     * which is being injected with property value jwt.expiration.time which is set to 900000 ms (15 minutes)
     */
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;


    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new SpringRedditException("Exception occurred while loading keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                /**
                 * We convert this expiration time which is in milliseconds to Date
                 * and passing that value to the setExpiration() while generating the JWT.
                 */
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    public String generateTokenWithUserName(String username) {
        return Jwts.builder()
                .setSubject(username)
                /**
                 * We convert this expiration time which is in milliseconds to Date
                 * and passing that value to the setExpiration() while generating the JWT.
                 */
                .setIssuedAt(from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore");
        }
    }

    /**
     * validateToken()
     * Once doFilterInternal() retrieves the token, we pass it to the validateToken() method "this method" of the JwtProvider class.
     * The validateToken method uses the JwtParser class to validate our JWT.
     * If you remember in the previous part, we created our JWT by signing it with the Private Key.
     * Now we can use the corresponding Public Key, to validate the token.
     * @param jwt
     * @return
     */
    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    /**
     * getPublicKey()
     * gets the Public key so that we can validate the token, method called in validateToken()
     * @return
     */
    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new SpringRedditException("Exception occured while retrieving public key from keystore");
        }
    }

    /**
     * getUsernameFromJWT()
     * Once the JWT is validated, we retrieve the username from the token by calling the getUsernameFromJWT() method.
     * Once we get the username, we retrieve the user using the UserDetailsService class and store the user inside the SecurityContext
     * @param token
     * @return
     */
    public String getUsernameFromJWT(String token) {
        Claims claims = parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    /**
     * We are also exposing the jwtExpirationInMillis field using the getJwtExpirationInMillis() method.
     * @return
     */
    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}