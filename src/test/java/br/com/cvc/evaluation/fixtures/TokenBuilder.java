package br.com.cvc.evaluation.fixtures;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.sql.Date;
import java.time.Instant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenBuilder {

    public String createJWT(final String subject, final Long ttlMillis) {
        //The JWT signature algorithm we will be using to sign the token
        final var signatureAlgorithm = SignatureAlgorithm.HS256;

        //We will sign our JWT with our ApiKey secret
        String SECRET_KEY =
                        "gxpDDSFYh2988jGRPvugceuysCNLdiubH0Fsu2YDBR0=";
        final var apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        final var signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        final var now = Instant.ofEpochMilli(System.currentTimeMillis());
        final var builder = Jwts.builder()
                        .setIssuer("testecvc")
                        .setIssuedAt(Date.from(now))
                        .setSubject(subject)
                        .signWith(signingKey, signatureAlgorithm);

        //if it has been specified, let's add the expiration
        builder.setExpiration(Date.from(now.plusMillis(ttlMillis)));

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
