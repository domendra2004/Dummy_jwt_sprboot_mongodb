package com.domen.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.domen.service.CustomUserDetailService;
import com.domen.util.JwtUtil;




@Component
public class JwtFilter extends OncePerRequestFilter {
	@Autowired 
	private JwtUtil jwtUtil;
	@Autowired
	CustomUserDetailService service;



	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		boolean isTokenValidate;

		 String authorizationHeader = httpServletRequest.getHeader("Authorization");

	        String token = null;
	        String username = null;

	        if (authorizationHeader != null) {
	            token = authorizationHeader;
	            username = jwtUtil.extractUsername(token);
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = service.loadUserByUsername(username);
				isTokenValidate=jwtUtil.validateToken(token, userDetails);
	            if (Boolean.TRUE.equals(isTokenValidate)) {
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken
	                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        }
	        filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}
