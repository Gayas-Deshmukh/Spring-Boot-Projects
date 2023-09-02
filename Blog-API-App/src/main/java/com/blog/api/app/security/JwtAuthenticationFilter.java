package com.blog.api.app.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.blog.api.app.exceptions.JwtException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	// This method will get trigger for every request that needs to be authenticated
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
	{
		// Token : Bearer 123654789952525...
		
		String requestToken	=	request.getHeader("Authorization");
		String userName     = 	null;
		String actualToken	=	null;
		
		if (requestToken != null && requestToken.startsWith("Bearer"))
		{
			actualToken = requestToken.substring(7);
			
			try 
			{
				userName = this.jwtTokenHelper.getUserNameFromToken(actualToken);
			}
			catch (IllegalArgumentException e) 
			{
				//throw new JwtException("Unable to get JWT token.");
			}
			catch (ExpiredJwtException e) 
			{
				//throw new JwtException("JWT token has expired.");
			}
			catch (MalformedJwtException e) 
			{
				//throw new JwtException("JWT token is invalid.");
			}
			
		}
		else
		{
			//throw new JwtException("JWT token does not begain with Bearer");
		}
		
		// Once we get token, now validate
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			
			if (this.jwtTokenHelper.validateToken(actualToken, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null , userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				//throw new JwtException("JWT token is invalid.");
			}
		}
		else
		{
			//throw new JwtException("UserName is null or context is not null");
		}
		
		filterChain.doFilter(request, response);
	}
}
