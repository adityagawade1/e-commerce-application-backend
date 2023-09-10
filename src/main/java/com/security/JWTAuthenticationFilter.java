package com.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.utils.Constants;
import com.utils.LOG;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	private final List<String> allowedOrigins = Arrays.asList("http://localhost:3000", "https://172.31.31.3",
			"http://20.219.137.179", "https://172.31.31.23", "https://14.139.108.234");

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader(Constants.AUTHORIZATION);
		LOG.info("Header "+header);
		String method = request.getMethod();
		LOG.info("Method: "+method);
		
		String username = null;
		String token = null;
		String role= null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token

//		if (method.equals("OPTIONS")) {
//			// Access-Control-Allow-Origin
//			String origin = request.getHeader("Origin");
//			response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
//			response.setHeader("Vary", "Origin");
//
//			// Access-Control-Max-Age
//			response.setHeader("Access-Control-Max-Age", "3600");
//
//			// Access-Control-Allow-Credentials
//			response.setHeader("Access-Control-Allow-Credentials", "true");
//
//			// Access-Control-Allow-Methods
//			response.setHeader("Access-Control-Allow-Methods", "POST,PUT,PATCH, GET, OPTIONS, DELETE");
//
//			// Access-Control-Allow-Headers
//			response.setHeader("Access-Control-Allow-Headers",
//					"Origin, Authorization, myheader, X-Requested-By,X-query,refreshtoken,token, X-Requested-With, Content-Type, Accept, "
//							+ "X-CSRF-TOKEN");
//
//			response.flushBuffer();
//		}else {
			if (header != null && header.startsWith("Bearer")) {
	            //looking good
	            token = header.substring(7);
	            try {

	                username = this.jwtUtil.getUsernameFromToken(token);
	                role=this.jwtUtil.getRole(token);

	            } catch (IllegalArgumentException e) {
	                LOG.info("Illegal Argument while fetching the username !!");
	               throw new IllegalArgumentException("Illegal Argument while fetching the username !!");
	            } catch (ExpiredJwtException e) {
	                LOG.info("Given jwt token is expired !!");
	               throw new IllegalArgumentException("Given jwt token is expired !!");
	            } catch (MalformedJwtException e) {
	                LOG.info("Some changed has done in token !! Invalid Token");
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();

	            }


	        } else {
	            LOG.info("Invalid Header Value !! ");
	        }


	        //
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {


	            //fetch user detail from username
	            UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
	            Boolean validateToken = this.jwtUtil.validateToken(token, userDetails);
	            if (Boolean.TRUE.equals(validateToken)) {

	                //set the authentication
	            	UsernamePasswordAuthenticationToken authentication = jwtUtil.getAuthenticationToken(token, SecurityContextHolder.getContext().getAuthentication(), userDetails);
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                logger.info("authenticated user " + username + ", setting security context");
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            	
	            	
//	            	
//	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//	                SecurityContextHolder.getContext().setAuthentication(authentication);

	                	LOG.info("User Validated");
	            }
		}
	        
//	}
		filterChain.doFilter(request, response);
		}}


