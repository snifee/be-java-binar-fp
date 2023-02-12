package com.kostserver.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostserver.model.EnumRole;
import com.kostserver.model.entity.Account;
import com.kostserver.model.response.UserDetailsRespond;
import com.kostserver.service.auth.AccountService;
import com.kostserver.service.auth.CustomOAuth2UserService;
import com.kostserver.utils.auth.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomOAuth2UserService oauthUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/v1/auth/**","/v1/public/**","/oauth/**","/login/**",
                        "/v2/api-docs/**",
                        "/swagger-ui/**",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger/**",
                        "/webjars/**").permitAll()
                .antMatchers("/v1/rooms/add","/v1/rooms/update").authenticated()
                .antMatchers("/v1/kost/**","/v1/rooms/update","/v1/rooms/add").hasAuthority(EnumRole.ROLE_USER_PEMILIK.name())
                .anyRequest()
                .authenticated()
                .and()
                        .httpBasic().disable();
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        DefaultOidcUser auth2User = (DefaultOidcUser) authentication.getPrincipal();

                        log.info(auth2User.getEmail());

                        Map<String,Object> userInfo = new LinkedHashMap<>();
                        userInfo.put("name",auth2User.getClaims().get("name"));
                        userInfo.put("picture",auth2User.getClaims().get("picture"));

                        Account account = accountService.processOAuthPostLogin(auth2User.getEmail(),userInfo);

//                        UserDetails userDetails = accountService.loadUserByUsername(account.getEmail());
//
//                        UserDetailsRespond udr = new UserDetailsRespond(account,account.getUserProfile());
//                        ObjectMapper objectMapper = new ObjectMapper();
//
//                        Map<String,Object> userDetailResponse = objectMapper.convertValue(udr,Map.class);
//
//                        String jwt = jwtUtils.generateToken(userDetails);
//                        Map<String,Object> data = new LinkedHashMap<>();
//                        data.put("access_token",jwt);
//                        data.put("user_details",userDetailResponse);
//
//                        Map<String,Object> res = new LinkedHashMap<>();
//                        res.put("status",HttpStatus.OK.name());
//                        res.put("message","success");
//                        res.put("data",data);

                        response.sendRedirect("/v1/auth/google/"+account.getEmail());

//                        response.setStatus(HttpServletResponse.SC_OK);
//                        response.getWriter().write(res.toString());
//                        response.getWriter().flush();


                    }
                });

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManager();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**",
                "/configuration/ui",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
