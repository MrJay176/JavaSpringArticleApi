package com.article.articleapi.Security.Filter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class CustomAuthorizationFilter extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        //Check if it is the login route
        System.out.println("Request DoFilter path"+request.getServletPath());
         if(request.getServletPath().equals("/login")){
             filterChain.doFilter(request,response);
         }else{
             String authorizationHeader = request.getHeader(AUTHORIZATION);
             if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                  String token = authorizationHeader.substring("Bearer Bearer ".length());
                  Algorithm algorithm  = Algorithm.HMAC256("secret".getBytes());
                  JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                  System.out.println("Tokennnn print out"+token);
                  DecodedJWT decodedJWT = jwtVerifier.verify(token);
                  String userName = decodedJWT.getSubject();
                  String[] role = decodedJWT.getClaim("role").asArray(String.class);
                  System.out.println("Roleee print out "+role[0]);
                 SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role[0]);
                 Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                 authorities.add(simpleGrantedAuthority);
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                         new UsernamePasswordAuthenticationToken(userName,null,authorities);
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                 filterChain.doFilter(request,response);
             }else{
                 filterChain.doFilter(request,response);
             }
         }
    }

}
