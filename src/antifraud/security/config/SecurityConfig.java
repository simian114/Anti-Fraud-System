package antifraud.security.config;

import antifraud.constants.RoleCodeEnum;
import antifraud.security.exceptions.handler.CustomAccessDeniedHandler;
import antifraud.security.exceptions.handler.RestAuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final int ENCODER_STRENGTH = 4;

    /**
     * password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCODER_STRENGTH);
    }

    /**
     * 401. authentication exception
     */
    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    /**
     * 403. authorization exception
     */
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(new RestAuthenticationEntryPoint()))
                .csrf(CsrfConfigurer::disable)
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler(customAccessDeniedHandler()))
                .headers(headers -> headers.frameOptions().disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/error").permitAll()
                        // .requestMatchers(HttpMethod.POST, "/api/auth/user", "/api/auth/user/").anonymous()
                        .requestMatchers(HttpMethod.POST, "/api/auth/user/**").permitAll()
                        // support & admin
                        .requestMatchers(HttpMethod.GET, "/api/auth/list/**").hasAnyRole(RoleCodeEnum.ADMINISTRATOR.name(), RoleCodeEnum.SUPPORT.name())
                        // merchant
                        .requestMatchers(HttpMethod.POST, "/api/antifraud/transaction/**").hasRole(RoleCodeEnum.MERCHANT.name())
                        // admin
                        .requestMatchers(HttpMethod.DELETE, "/api/auth/user/**").hasRole(RoleCodeEnum.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.PUT, "/api/auth/access/**").hasRole(RoleCodeEnum.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.PUT, "/api/auth/role/**").hasRole(RoleCodeEnum.ADMINISTRATOR.name())
                        // h2
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/actuator/shutdown").permitAll() // test용
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
