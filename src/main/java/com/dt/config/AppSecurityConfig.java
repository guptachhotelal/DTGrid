package com.dt.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@PostConstruct
	public void initialize() {
		DocCustomConverter.add(new ObjectMapper());
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http.authorizeHttpRequests(auth -> auth.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.requestMatchers(mvc.pattern("/resources/**")).permitAll()
				.requestMatchers(mvc.pattern("*/**")).hasAnyRole("USER", "ADMIN").anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/home", true))
				.logout(logout -> logout.permitAll()).httpBasic(withDefaults());
		return http.csrf(csrf -> csrf.disable()).build();
	}

	@Scope("prototype")
	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	@Bean
	UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build();
		UserDetails user = User.withUsername("user").password(encoder.encode("user")).roles("USER").build();
		return new InMemoryUserDetailsManager(admin, user);
	}
}
