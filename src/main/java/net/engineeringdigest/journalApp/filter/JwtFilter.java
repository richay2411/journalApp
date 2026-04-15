package net.engineeringdigest.journalApp.filter;

import net.engineeringdigest.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter  extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtil.validateToken(jwt)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
    }
}





//package net.engineeringdigest.journalApp.filter;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import net.engineeringdigest.journalApp.utils.JwtUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.util.Date;
//
//import static javax.crypto.Cipher.SECRET_KEY;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Autowired
//    private UserDetails userDetails;
//
//    private SecretKey  getSigningKey(){
//        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }
//
//    public String extractUsername(String token){
//        return extractAllClaims(token).getSubject();
//    }
//
//    public Date extractExpiration(String token){
//        return extractAllClaims(token).getExpiration();
//    }
//
//    public String extractClaim(String token, String claim){
//        return extractAllClaims(token).get(claim, String.class);
//    }
//
//    private Claims extractAllClaims(String token){
//        return Jwts.parser()
//                .verifyWith(getSigningKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, filterChain chain) throws ServletException, IO )
//       String authorizationHeader = request.getHeader("Authorization");
//       String username = null;
//       String jwt = null;
//       if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//           jwt = authorizationHeader.substring(7);
//           username = jwtUtil.extractUsername(jwt);
//    }
//       if(username != null) {
//           userDetails userDetails = userDetailsService.loadUserByUsername(username);
//           if(jwtUtil.validateToken(jwt, userDetails.getUsername())) {
//               UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthentication();
//               auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//               securityContextHolder.getContext().setAuthentication(null);
//           }
//    }
//       response.addHeader("admin", "shinu");
//       chain.doFilter(request, response);
//}
//}
//}