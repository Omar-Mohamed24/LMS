package com.lms.LMS.config;

import com.lms.LMS.models.Role;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
public class SecurityConfig
{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf(AbstractHttpConfigurer::disable)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/users/**").permitAll()
                        .requestMatchers( "/api/courses/quizzes/submissions/**").hasAnyRole(Role.INSTRUCTOR.name(), Role.STUDENT.name())
                        .requestMatchers("/api/courses/assignments/submissions/**").hasAnyRole(Role.INSTRUCTOR.name(), Role.STUDENT.name())

                        // Restrict grading endpoints to instructors and admins
                        .requestMatchers( "/api/courses/quizzes/submissions/grade/**").hasAnyRole(Role.INSTRUCTOR.name(), Role.ADMIN.name())
                        .requestMatchers( "/api/courses/assignments/submissions/grade/**").hasAnyRole(Role.INSTRUCTOR.name(), Role.ADMIN.name())
                        .requestMatchers("/api/courses/**").hasAnyRole(Role.INSTRUCTOR.name(), Role.ADMIN.name())
                        .requestMatchers("/api/students/**").hasRole(Role.STUDENT.name())
                        .requestMatchers("/api/teacher/**").hasRole(Role.INSTRUCTOR.name())
                        .anyRequest().authenticated()
                ).sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(
                        e -> e.authenticationEntryPoint(
                                (request, response, authException) -> response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
                        )
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
