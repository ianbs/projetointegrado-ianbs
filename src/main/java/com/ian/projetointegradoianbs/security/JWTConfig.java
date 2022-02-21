package com.ian.projetointegradoianbs.security;

import com.ian.projetointegradoianbs.services.UsuarioServices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@CrossOrigin("*")
public class JWTConfig extends WebSecurityConfigurerAdapter {

    private final UsuarioServices usuarioService;
    private final PasswordEncoder passwordEncoder;

    public JWTConfig(UsuarioServices usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().authorizeRequests().antMatchers(HttpMethod.POST,
                "/login")
                .permitAll().anyRequest().authenticated().and()
                .addFilter(new JWTAuthFilter(authenticationManager()))
                .addFilter(new JWTValidateFilter(authenticationManager())).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    CorsConfigurationSource corsConfiguration() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedHeader("*");

        // corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
        // corsConfiguration.setAllowedHeaders(
        // Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Headers",
        // "Authorization",
        // "Access-Control-Allow-Headers", "Origin", "Accept", "X-Requested-With",
        // "Content-Type",
        // "Access-Control-Request-Method", "Access-Control-Request-Headers"));

        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
