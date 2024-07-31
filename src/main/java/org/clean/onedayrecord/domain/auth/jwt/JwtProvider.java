package org.clean.onedayrecord.domain.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.clean.onedayrecord.domain.member.entity.MemberRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    //== Access Token ==//
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private int expiration;

    //== Refresh Token==//
    @Value("${jwt.secretKey-rt}")
    private String secretKeyRt;

    @Value("${jwt.expiration-rt}")
    private int expirationRt;
    public Claims getClaims(String bearerToken){

        String token = bearerToken.substring(7);
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String createAccessToken(Long id, MemberRole role) {
        Claims claims = Jwts.claims().setSubject(Long.toString(id));
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration * 60 * 1000L)) // 30분
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Long id, MemberRole role) {
        Claims claims = Jwts.claims().setSubject(Long.toString(id));
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expirationRt * 60 * 1000L)) // 10일
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean isValidToken(String bearerToken) {
        return bearerToken.substring(0, 7).equals("Bearer ");
    }
}
