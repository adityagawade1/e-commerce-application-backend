package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	
		@Autowired
		private JWTAuthenticationFilter filter;
	

		 @Bean
		  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		    http
		        .csrf()
		        .disable()
		        .cors(cors -> cors.configurationSource(corsConfiguration()))
		        .authorizeHttpRequests()
		        .requestMatchers("/non-authenticate/**", "/authenticate/**"
		               
		        ).permitAll()
		        .requestMatchers("/category/**").hasRole("ADMIN")
		        .requestMatchers("/user/**",",/authenticate/refresh-token")
		        .authenticated()
		        .anyRequest().authenticated()
		        .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
		          .sessionManagement()
		          .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		    return http.build();
		  }
		 
		 @Bean
		 CorsConfigurationSource corsConfiguration() {
			 CorsConfiguration corsConfiguration = new CorsConfiguration();
			 corsConfiguration.addAllowedOriginPattern("http://localhost:3000");
			 corsConfiguration.addExposedHeader("Content-Hash");
			 corsConfiguration.addAllowedMethod("POST");
			 corsConfiguration.addAllowedMethod(HttpMethod.GET);
			 corsConfiguration.addAllowedMethod("DELETE");
			 corsConfiguration.addAllowedHeader("AUTHORIZATION");
			 corsConfiguration.addAllowedHeader("x-query");
			 corsConfiguration.addAllowedHeader("content-type");
			 
			 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			 source.registerCorsConfiguration("/**", corsConfiguration);
			 return source;
		 }
}
