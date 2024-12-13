/* AdminService.java
 * Parking Lot Management Service
 * 관리자 Service
 * 작성자 : semi_lion2 (고민정, 박청조, 이홍비, 허선호)
 * 최종 수정 날짜 : 2024.12.12
 *
 * ========================================================
 * 프로그램 수정 / 보완 이력
 * ========================================================
 * 작업자       날짜       수정 / 보완 내용
 * ========================================================
 * 박청조    2024.12.12    AdminService 생성 - 로그인 관련 로직 구현
 * ========================================================
 */

package team2.parking.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team2.parking.entity.Admin;
import team2.parking.repository.AdminRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;

    // 스프링 시큐리티가 사용하는 UserDetails 객체로 관리자 데이터 반환
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(
                admin.getId(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority(admin.getRole()))
        );

    }
}
