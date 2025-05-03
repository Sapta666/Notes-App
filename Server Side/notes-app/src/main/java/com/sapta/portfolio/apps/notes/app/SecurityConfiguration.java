package com.sapta.portfolio.apps.notes.app;

import java.io.ObjectInputFilter.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private JWTFilter jwtFilter;
	@Autowired
	private AppUserDetailService appUserDetailService;

	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
//		http.httpBasic(customizer -> customizer.disable());		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.authorizeHttpRequests(customizer -> {
			customizer.requestMatchers("/auth/**").permitAll().anyRequest().authenticated();
//			customizer.anyRequest().permitAll();
		})
		.csrf(customizer -> customizer.disable())
		.formLogin(customizer -> customizer.disable());
		http.sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// Set custom authentication provider
		http.authenticationProvider(authenticationProvider());
//        http.cors(Customizer.withDefaults());
//		http.cors(customizer -> customizer.configure(http));
//        http.cors(customizer -> customizer.disable());
//        http.cors(customizer -> customizer.configurationSource(request -> request.get));

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(5);
	}

	/*
	 * Authentication provider configuration Links UserDetailsService and
	 * PasswordEncoder
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.appUserDetailService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}

	/*
	 * Authentication manager bean Required for programmatic authentication (e.g.,
	 * in /generateToken)
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

}
