package agricole.simulatore.mutuoCard.security.jwt;

import agricole.simulatore.mutuoCard.security.SecurityPrincipal;
import agricole.simulatore.mutuoCard.utils.exception.JwtValidationException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    public static final String BEARER_TOKEN_PREFIX = "Bearer";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String headerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (Objects.nonNull(headerToken)) {
                String token = headerToken.replace(BEARER_TOKEN_PREFIX, StringUtils.EMPTY).trim();
                try {
                    if (jwtUtil.isTokenValid(token)) {
                        AbstractAuthenticationToken authentication;
                        authentication = new UsernamePasswordAuthenticationToken(
                            new SecurityPrincipal(jwtConfig, token),
                            null,
                            jwtUtil.getJwtAuthorities(token));
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (JwtValidationException exception) {
                    request.setAttribute("exception", exception.getMessage());
                }
            }
        }
        chain.doFilter(request, response);
    }
}