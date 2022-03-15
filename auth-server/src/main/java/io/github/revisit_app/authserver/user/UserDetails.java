package io.github.revisit_app.authserver.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDetails implements UserDetailsService {

  private final UserRepo ur;

  private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

  @Override
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = ur.findByUsername(username);
    if (user == null) {
      log.error("User: {} not found", username);
      throw new UsernameNotFoundException("User: " + username + " not found");
    }
    authorities.add(new SimpleGrantedAuthority("read"));
    authorities.add(new SimpleGrantedAuthority("write"));

    return new org.springframework.security.core.userdetails.User(String.valueOf(user.getId()), user.getPassword(),
        authorities);
  }

}
