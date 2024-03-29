package com.blog.api.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.api.app.security.CustomUserDetailsService;
import com.blog.api.app.security.JwtAuthenticationEntryPoint;
import com.blog.api.app.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity()
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig 
{
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final String [] PUBLIC_URLS = {"/api/auth/login","/v3/api-docs","/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/webjars/**" };
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf()													
		.disable()
		.authorizeHttpRequests()
		.requestMatchers(PUBLIC_URLS)//.hasAnyRole("ADMIN")
		.permitAll()
		.requestMatchers(HttpMethod.GET)
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
    }
	
	@Bean
	public AuthenticationManager authManager(HttpSecurity http) throws Exception 
	{
	     return http.getSharedObject(AuthenticationManagerBuilder.class)
	    		 .userDetailsService(this.customUserDetailsService)
	    		 .passwordEncoder(bCryptPasswordEncoder())
	    		 .and()
	    		 .build();
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}
}
