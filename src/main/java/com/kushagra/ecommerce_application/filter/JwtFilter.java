package com.kushagra.ecommerce_application.filter;

import com.kushagra.ecommerce_application.service.UserDetailsServiceImpl;
import com.kushagra.ecommerce_application.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Field;
@Component

public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtility jwtUtility;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws ServletException, IOException {
        String auth =request.getHeader("Authorization");
        String username=null;
        String jwt=null;
        if(auth!=null&& auth.startsWith("Bearer")){
            jwt=auth.substring(7);
            username=jwtUtility.extractUsername(jwt);
        }
        if(username!=null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(username);
            if(jwtUtility.isValid(jwt)){
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        }
        chain.doFilter(request,response);

    }
}
