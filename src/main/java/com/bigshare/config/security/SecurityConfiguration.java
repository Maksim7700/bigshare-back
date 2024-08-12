package com.bigshare.config.security;

import com.bigshare.config.jwt.JWTAutentificatedFilter;
import com.bigshare.config.jwt.JWTTokenHelper;
import com.bigshare.service.auth.TokenService;
import com.bigshare.service.user.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private final UserDetailServiceImpl userDetailService;
    private final JWTTokenHelper jwtTokenHelper;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final TokenService tokenService;

    @Autowired
    public SecurityConfiguration(@Lazy UserDetailServiceImpl userDetailService, JWTTokenHelper jwtTokenHelper, AuthenticationEntryPoint authenticationEntryPoint, TokenService tokenService) {
        this.userDetailService = userDetailService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.tokenService = tokenService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**", "/api/v1/auth/login", "/registration").permitAll();
        http.addFilterBefore(new JWTAutentificatedFilter(userDetailService, jwtTokenHelper, tokenService),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
