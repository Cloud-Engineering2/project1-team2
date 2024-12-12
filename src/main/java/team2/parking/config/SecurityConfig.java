/* SecurityConfig.java
 * Parking Lot Management Service
 * 스프링 시큐리티 관련 Config
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.12    로그인 사용을 위해 스프링 시큐리티 Config 정의
 *                       테스트 레벨에서는 모든 요청 permitAll() 지정
 *                       개발 마무리 단계에서 url 별 접근 제어 예정
 *
 * ========================================================
 */

package team2.parking.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화
                .csrf(csrf -> csrf.disable())
                // 요청 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 정적 리소스는 인증 없이 허용
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        // /admin/success 요청은 인증 필요
                        .requestMatchers("/admin/success").authenticated()
                        // 이외의 요청은 인증 필요 x
                        .anyRequest().permitAll()
                )
                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/admin/login") // 커스텀 로그인 페이지
                        .loginProcessingUrl("/login") // /login post 요청
                        .defaultSuccessUrl("/admin/success") // 로그인 성공 시 리다이렉트 URL
                        .permitAll()
                )
                // 로그아웃 설정
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/admin/login") // 로그아웃 성공 후 리다이렉트 URL
                        .permitAll()
                );

        return http.build();
    }



    // 데이터 암호화 X -> encoder 지정 X
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}
