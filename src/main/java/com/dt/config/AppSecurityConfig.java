package com.dt.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	private static final String[] ROLES = { "USER", "ADMIN" };

	@PostConstruct
	public void initialize() {
		DocCustomConverter.add(new ObjectMapper());
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				auth -> auth.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll().requestMatchers("/resources/**")
						.permitAll().requestMatchers("/**").hasAnyRole(ROLES).anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/home", true))
				.logout(LogoutConfigurer::permitAll).httpBasic(withDefaults());
		return http.csrf(csrf -> csrf.disable()).build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin")).roles(ROLES[0]).build();
		UserDetails user = User.withUsername("user").password(encoder.encode("user")).roles(ROLES[1]).build();
		return new InMemoryUserDetailsManager(admin, user);
	}
}
