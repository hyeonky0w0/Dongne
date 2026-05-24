package com.example.umc10th.global.config;

import com.example.umc10th.global.Security.exception.CustomAccessDenied;
import com.example.umc10th.global.Security.exception.CustomEntryPoint;
import com.example.umc10th.global.Security.filter.JwtAuthFilter;
import com.example.umc10th.global.Security.handler.OAuthSuccessHandler;

import com.example.umc10th.global.Security.service.CustomOAuthService;
import com.example.umc10th.global.Security.service.CustomUserDetailsService;
import com.example.umc10th.global.Security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.PkceParameterNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomOAuthService customOAuthService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final String[] allowUris = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/api/auth/**",
            "/oauth/authorize/**",
            "/oauth/callback/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomOAuthService customOAuthService) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(allowUris).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth -> oauth
                        .authorizationEndpoint(auth -> auth
                                .baseUri("/oauth/authorize")
                                .authorizationRequestResolver(authorizationRequestResolver())
                                .authorizationRequestRepository(authorizationRequestRepository()) // 추가
                        )
                        .redirectionEndpoint(redirect -> redirect
                                .baseUri("/oauth/callback/**")
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuthService)
                        )
                        .successHandler(oAuthSuccessHandler())
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomEntryPoint())
                        .accessDeniedHandler(new CustomAccessDenied())
                );
        return http.build();
    }


    @Bean
    public HttpSessionOAuth2AuthorizationRequestRepository authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }



    @Bean
    public OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
        DefaultOAuth2AuthorizationRequestResolver resolver =
                new DefaultOAuth2AuthorizationRequestResolver(
                        clientRegistrationRepository, "/oauth/authorize");


        resolver.setAuthorizationRequestCustomizer(customizer ->
                customizer
                        .attributes(attrs -> attrs.remove(PkceParameterNames.CODE_VERIFIER))
                        .additionalParameters(params -> {
                            params.remove(PkceParameterNames.CODE_CHALLENGE);
                            params.remove(PkceParameterNames.CODE_CHALLENGE_METHOD);
                        })
        );
        return resolver;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtUtil, customUserDetailsService);
    }

    @Bean
    public OAuthSuccessHandler oAuthSuccessHandler() {
        return new OAuthSuccessHandler(jwtUtil);
    }
}
