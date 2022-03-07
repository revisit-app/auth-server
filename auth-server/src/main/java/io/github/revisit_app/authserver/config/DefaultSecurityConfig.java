package io.github.revisit_app.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
public class DefaultSecurityConfig {

  private final PasswordEncoder pe;

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests.anyRequest().authenticated()
			)
			.formLogin(withDefaults());
		return http.build();
	}

	@Bean
	UserDetailsService users() {
		UserDetails user = User.withUsername("user")
				.password(pe.encode("password"))
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(user);
	}

}
