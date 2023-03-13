package com.sparta.myboard.config;
import com.sparta.myboard.jwt.JwtAuthFilter;
import com.sparta.myboard.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests().antMatchers("/api/member/signup").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**",
                        "/v2/api-docs", "/webjars/**").permitAll()   //스웨거 접근 허용 설정
                .antMatchers("/api/member/login").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/boards").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/boards/{id}").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/boards").permitAll()
                .antMatchers("/api/boards/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
/*
현재 스웨거가 실행되지 않는다.
jwt필터를 걸어서 안되는 것 같은데 addFilterBefore를 사용했기 때문에
필터 전에 jwt가 먼저 걸리는 상황이다.
스웨거를 위한 메서드를 정의해서 독립적으로 만드는 것이 좋은가?
jwt필터를 addFilterBefore가 아닌 다른쪽으로 바꾸는 것이 좋은가?
*/

//        http.formLogin().loginPage("/api/user/login-page").permitAll();
//        이건 formLogin이 없는상황이라 필요 없는 것이 아닌가
        http.exceptionHandling().accessDeniedPage("/api/member/forbidden");

        return http.build();
    }

}