package com.krungsri.backendtest.helper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Component
public class JWTFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String authorization = request.getHeader("authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new ServletException("401 - UNAUTHORIZED");
        }

        try {
            final Claims claims = Jwts.parser().setSigningKey("123#&*zcvAWEE999")
                    .parseClaimsJws(authorization.substring(7)).getBody();
            servletRequest.setAttribute("claims", claims);
        } catch (final SignatureException e) {
            throw new ServletException("401 - UNAUTHORIZED");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
