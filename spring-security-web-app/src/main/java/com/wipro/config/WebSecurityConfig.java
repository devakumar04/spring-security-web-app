package com.wipro.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	private static final String ENCODED_PASSWORD_USER = "password"; //password
	private static final String ENCODED_PASSWORD_ADMIN = "admin@123";
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/", "/home").permitAll()
				.anyRequest().authenticated()
				)
		.formLogin((form) -> form
				.loginPage("/login")
				.permitAll()
				)
//		.formLogin(withDefaults())
		.logout((logout) -> logout.permitAll());
		
		
		return http.build();
	}
	//	@Bean
	//	public UserDetailsService userDetailsService() {	
	//		
	//
	//		UserDetails user = User.withDefaultPasswordEncoder()
	//				.username("user")
	//				.password("password")
	//				.roles("USER")
	//				.build();
	//
	//		UserDetails admin = User.withDefaultPasswordEncoder()
	//				.username("admin")
	//				.password("admin@123")
	//				.roles("USER", "ADMIN")
	//				.build();
	//		return new InMemoryUserDetailsManager(user, admin);
	//	}
	@Bean
	public UserDetailsService userDetailsService() {
		InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
		
		UserDetails user =
				User.withUsername("user")
				.password(passwordEncoder().encode(ENCODED_PASSWORD_USER))
				.roles("USER")
//				.authorities("read")
				.build();
		
		UserDetails admin =
				User.withUsername("admin")
				.password(passwordEncoder().encode(ENCODED_PASSWORD_ADMIN))
				.roles("ADMIN")
//				.authorities("write")
				.build();
		userDetailsService.createUser(user);
		userDetailsService.createUser(admin);
		return userDetailsService;
	}
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
