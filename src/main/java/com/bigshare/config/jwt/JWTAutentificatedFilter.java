package com.bigshare.config.jwt;

import com.bigshare.exceptions.ErrorCode;
import com.bigshare.exceptions.ErrorObject;
import com.bigshare.service.TokenService;
import com.bigshare.utils.SecurityUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JWTAutentificatedFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JWTTokenHelper jwtTokenHelper;
    private final TokenService tokenService;

    public JWTAutentificatedFilter(UserDetailsService userDetailsService, JWTTokenHelper jwtTokenHelper, TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.tokenService = tokenService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authToken = jwtTokenHelper.getToken(request);
            if (null != authToken) {
                if (StringUtils.hasText(authToken)) {
                    Claims claims = jwtTokenHelper.getJwtBody("Bearer " + authToken);
                    if (tokenService.isOnBlacklist(claims.getId())) {
                        throw new JwtException("Token is invalid");
                    }
                }
                String username = jwtTokenHelper.getUsernameFromToken(authToken);
                if (null != username) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtTokenHelper.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            SecurityUtils.sendJsonResponse(response, HttpServletResponse.SC_UNAUTHORIZED, new ErrorObject(HttpStatus.UNAUTHORIZED.value(), ErrorCode.UNAUTHORIZED, ex.getMessage()));
        }
    }
}
