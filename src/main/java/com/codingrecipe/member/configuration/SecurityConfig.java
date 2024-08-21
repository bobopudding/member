package com.codingrecipe.member.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = org.springframework.security.core.userdetails.User.withUsername("zerotrust")
                .password(passwordEncoder.encode("zerotrust"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").authenticated() // 인증이 필요한 요청
                        .anyRequest().permitAll() // 그 외 요청은 허용
                )
                .formLogin(form -> form
                        .loginPage("/admin/login") // 사용자 정의 로그인 페이지
                        .loginProcessingUrl("/admin/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/admin/member-list", true) // 로그인 성공 시 이동할 URL
                        .permitAll() // 로그인 페이지는 인증 없이 접근 허용
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                        .permitAll() // 로그아웃 페이지는 인증 없이 접근 허용
                )
                .csrf(csrf -> csrf.disable()); // CSRF 보호 비활성화

        return http.build(); // SecurityFilterChain 객체 생성
    }
}
