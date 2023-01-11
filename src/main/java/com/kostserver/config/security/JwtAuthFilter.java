package com.kostserver.config.security;

import com.kostserver.service.auth.AccountService;
import com.kostserver.utils.auth.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenBearer = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (tokenBearer != null && tokenBearer.startsWith("Bearer ")){
            token = tokenBearer.substring("Bearer ".length());

            try{
                username = jwtUtils.extractUsername(token);

                UserDetails userDetails = accountService.loadUserByUsername(username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }else {
                    log.info("Invalid Token");
                }
            }catch (Exception e){
                log.info(e.getMessage());
            }
        }else {
            log.info("Invalid Bearer token format");
        }
        filterChain.doFilter(request,response);
    }
}
