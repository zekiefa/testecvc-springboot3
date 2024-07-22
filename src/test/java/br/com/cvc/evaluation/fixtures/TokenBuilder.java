package br.com.cvc.evaluation.fixtures;

import java.sql.Date;
import java.time.Instant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;

public class TokenBuilder {

    public String createJWT(final String subject, final Long ttlMillis) {
        //The JWT signature algorithm we will be using to sign the token
        //final var signatureAlgorithm = Jwts.SIG.HS256.key().build().getAlgorithm();

        //We will sign our JWT with our ApiKey secret
        String SECRET_KEY =
                        "gxpDDSFYh2988jGRPvugceuysCNLdiubH0Fsu2YDBR0=";
        final var apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        //final var signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm);
        final var key = Keys.hmacShaKeyFor(apiKeySecretBytes);

        //Let's set the JWT Claims
        final var now = Instant.ofEpochMilli(System.currentTimeMillis());
        final var builder = Jwts.builder()
                        .issuer("testecvc")
                        .issuedAt(Date.from(now))
                        .subject(subject)
                        .signWith(key, Jwts.SIG.HS256);

        //if it has been specified, let's add the expiration
        builder.expiration(Date.from(now.plusMillis(ttlMillis)));

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
