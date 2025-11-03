package com.juaracoding.security;

import com.juaracoding.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * class untuk fungsional Json Web Token
 */
@Component
public class JwtUtility {

    /** variable untuk menentukan OK */
    private String ok;

    /** variable untuk menentukan OK */
    public Map<String, Object> mappingBodyToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", claims.get("id"));
        map.put("username", claims.getSubject());
        map.put("noHp", claims.get("hp"));
        map.put("namaLengkap", claims.get("naleng"));
        map.put("email", claims.get("em"));
        return map;
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(JwtConfig.getSecretKey()).parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /** fungsi ini dipanggil saat login , untuk pembentukan token JWT */
    public String doGenerateToken(Map<String, Object> claims, String subject) {
        Long timeMilis = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)//payload
                .setSubject(subject)//username
                .setIssuedAt(new Date(timeMilis))//info terbitnya token
                .setExpiration(new Date(timeMilis + JwtConfig.getTimeExpiration()))// waktu saat ini + konfigurasi lama nya token (30 menit) ex : terbit 12:00 expired 12:30
                .signWith(SignatureAlgorithm.HS512, JwtConfig.getSecretKey()).compact();//
    }

    public Boolean validateToken(String token) {
        /** Sudah otomatis tervalidaasi jika expired date masih aktif */
        String username = getUsernameFromToken(token);
        return (username!=null && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
}
