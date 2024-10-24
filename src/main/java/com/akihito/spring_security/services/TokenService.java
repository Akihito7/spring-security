package com.akihito.spring_security.services;

import com.akihito.spring_security.models.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(UserModel user){

        try {
            var algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("akihito-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpiration())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String valideToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
             JWT.require(algorithm)
                    .withIssuer("akihito-api")
                    .build()
                    .verify(token)
                    .getSubject();

            DecodedJWT decodedJWT = JWT.decode(token);
            String subject = decodedJWT.getSubject();
            return subject;
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant genExpiration(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
