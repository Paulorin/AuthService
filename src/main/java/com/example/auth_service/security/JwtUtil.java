package com.example.auth_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${AccessjwtExpirationMin}")
    private int jwtExpirationMin;

    @Value("${RefreshjwtExpirationDay}")
    private int refreshjwtExpirationDay;

    @Value("${jwtAccessCookieName}")
    private String jwtAccessCookieName;

    @Value("${jwtRefreshCookieName}")
    private String jwtRefreshCookieName;

    // Potentially change signing algorithm to asymmetric
    public String generateAccessJwt(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(jwtExpirationMin).toInstant());

        return JWT.create()
                .withSubject(username)
                .withIssuer("example.auth-service")
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public String generateRefreshJwt(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusDays(refreshjwtExpirationDay).toInstant());

        return JWT.create()
                .withSubject(username)
                .withIssuer("example.auth-service")
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    public Boolean verifyRefreshToken(String refreshToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret)).build();

        DecodedJWT verifiedJwt = verifier.verify(refreshToken);

        System.out.println(verifiedJwt.getPayload());

        Map<String, Claim> claims = verifiedJwt.getClaims();

        System.out.println(verifiedJwt.getClaim("sub"));
        for (String key : claims.keySet()) {
            System.out.println(key + " " + claims.get(key).toString());
        }

        return Boolean.TRUE;
    }

    public String getUsernameFromJwt(String jwt) {
        return JWT.decode(jwt).getClaim("sub").asString();
    }
}
