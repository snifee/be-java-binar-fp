package com.kostserver.config.security;

import com.kostserver.service.auth.AccountService;
import com.kostserver.utils.auth.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class JwtAuthFilter extends OncePerRequestFilter {


    private List<String> excludeUrlPatterns = new ArrayList<String>(Arrays.asList("/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui/",
            "/v3/api-docs",
            "/webjars/**",
            "/v1/auth"));

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtUtils jwtUtils;
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenBearer = request.getHeader("Authorization");
        String username = null;
        String token = null;
        try{
            if (tokenBearer != null && tokenBearer.startsWith("Bearer ")){
                token = tokenBearer.substring("Bearer ".length());

                username = jwtUtils.extractUsername(token);

                UserDetails userDetails = accountService.loadUserByUsername(username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(upat);
                }
            }else {
                log.info("Invalid Bearer token format");
                throw new JwtException("Invalid Bearer token format");
            }
            filterChain.doFilter(request,response);
        }catch (Exception e){
            resolver.resolveException(request,response,null,e);
            log.info(e.getMessage());
        }


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        log.info("jwt filter path " + path + "| "+excludeUrlPatterns.stream().anyMatch(p -> request.getRequestURI().startsWith(p)));
        if (excludeUrlPatterns.stream().anyMatch(p -> request.getRequestURI().startsWith(p))) {
            return true;
        } else {
            return false;
        }
    }
}
