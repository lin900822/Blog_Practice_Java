package com.lin.blog.portal.service.impl;

import com.lin.blog.portal.exception.CustomAuthenticationException;
import com.lin.blog.portal.vo.AuthenticationVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTService
{
    @Resource
    private AuthenticationManager authenticationManager;

    private final String KEY = "Thisisasimplecontentmanagementsystem";

    public String generateToken(AuthenticationVO authenticationVO)
    {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(authenticationVO.getUsername(), authenticationVO.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 2);

        Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) userDetails.getAuthorities();
        String[] auth = new String[authorities.size()];
        int i = 0;
        for(GrantedAuthority a : authorities){
            auth[i] = a.getAuthority();
            i++;
        }

        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        claims.put("authorities", auth);
        claims.setExpiration(calendar.getTime());
        claims.setIssuer("A Fantastic Blog");

        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> parseToken(String token)
    {
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());

        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();

        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();

        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
