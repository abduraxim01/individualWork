package org.example.individualwork.configuration;

import org.example.individualwork.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class Configuration {

    @Autowired
    private AuthenticationService authService;

    private final String MAHSULOT_API = "/api/mahsulot";

    private final String SOTUVCHI_API = "/api/sotuvchi";

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requestsConfigurer -> {
                    requestsConfigurer
                            .requestMatchers(SOTUVCHI_API + "/getAllSotuvchiAsDTO",
                                    SOTUVCHI_API + "/addSotuvchi",
                                    SOTUVCHI_API + "/getInactiveSotuvchi",
                                    SOTUVCHI_API + "/changeSotuvchiDate").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(SOTUVCHI_API + "/changeSotuvchiDetails").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")

                            .requestMatchers(MAHSULOT_API + "/getAllMahsulot",
                                    MAHSULOT_API + "/getInActiveMahsulot/{id}").hasAuthority("ROLE_ADMIN")
                            .requestMatchers(MAHSULOT_API + "/addMahsulot").hasAnyAuthority("ROLE_USER")
                            .requestMatchers(MAHSULOT_API + "/getAllMahsulotByID").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                            .anyRequest().permitAll();
                })
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(authService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
