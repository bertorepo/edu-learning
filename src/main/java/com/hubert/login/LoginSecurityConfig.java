package com.hubert.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class LoginSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().and()
		.authorizeRequests((auth) -> auth.antMatchers("/courses", "/books", "/freebies").hasAnyRole("ADMIN", "USER")
				.antMatchers("/admin").hasRole("ADMIN")
				.mvcMatchers("/category/**", "/admin/**").hasRole("ADMIN")
				.antMatchers("/", "/home", "/dashboard").hasAnyRole("ADMIN", "USER")
				.antMatchers("/login").permitAll())
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dashboard?auth=true")
				.failureUrl("/login?error=true").permitAll()
				.and()
				.logout()		
				.logoutUrl("/login?logout=true").permitAll()
				.logoutSuccessUrl("/logout").permitAll()
				.invalidateHttpSession(true)
				.and().httpBasic();
				
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
