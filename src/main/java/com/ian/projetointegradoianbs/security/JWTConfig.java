package com.ian.projetointegradoianbs.security;

import java.util.Arrays;

import com.ian.projetointegradoianbs.services.UsuarioServices;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@CrossOrigin(origins = "*")
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
        JWTAuthFilter jwtAuthFilter = new JWTAuthFilter(authenticationManager());
        jwtAuthFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/usuario/token/refresh/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(jwtAuthFilter);
        http.addFilter(new JWTValidateFilter(authenticationManager()))
                .sessionManagement();
        // http.csrf().disable().cors().and().authorizeRequests()
        // .antMatchers(HttpMethod.POST, "/login").permitAll()
        // .antMatchers(HttpMethod.POST, "/api/usuario/*")
        // .permitAll().anyRequest().authenticated().and()
        // .addFilter(new JWTAuthFilter(authenticationManager()))
        // .addFilter(new
        // JWTValidateFilter(authenticationManager())).sessionManagement()
        // .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    CorsConfigurationSource corsConfiguration() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin",
                "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin",
                "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);
        config.setAllowedOrigins(
                Arrays.asList("http://localhost:3000", "https://projetointegrado-ianbs-front.vercel.app"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
