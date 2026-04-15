package net.engineeringdigest.journalApp.utils;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

//    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";
    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$VTaK+HaV^uvCHEFsEVfypW";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ","JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 5 minutes expiration time
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }


}





//package net.engineeringdigest.journalApp.utils;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class JwtUtil {
//
//    private String SECRET_KEY = "Tak+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";
//
//    private SecretKey getSigningKey(){
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }
//
//    private String extractUsername(String token){
//        return extractAllClaims(token).getSubject();
//    }
//
//    private Date extractExpiration(String token){
//        return extractAllClaims(token).getExpiration();
//    }
//
//    private String extractClaims(String token, String claim) {
//        return  extractAllClaims(token).get(claim, String.class);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts.parser()
//                .verifyWith(getSigningKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//private Boolean isTokenExpired(String token){
//            return extractExpiration(token).before(new Date());
//}
//
//    public String generateToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, username);
//    }
//
//    private String createToken(Map<String, Object> claims, String subject){
//            return Jwts.builder()
//                    .claims(claims)
//                    .subject(subject)
//                    .header().empty().add("type","JWT")
//                    .and()
//                    .issuedAt(new Date(System.currentTimeMillis()))
//                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))  // 5 minutes expiration test
//                    .signWith(getSigningKey())
//                    .compact();
//    }
//
//    public Boolean validateToken(String token, String username){
//        final String extractedUsername = extractUsername(token);
//        return (extractedUsername.equals(username) && !isTokenExpired(token));
//    }
//}
//
