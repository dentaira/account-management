package dentaira.accountmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // todo セキュリティはいったん無効化
    //        http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
    //                .httpBasic(withDefaults())
    return http.authorizeHttpRequests((authz) -> authz.anyRequest().permitAll())
        .csrf(AbstractHttpConfigurer::disable)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
