package com.example.catalog.security.service;

import com.example.catalog.user.entity.User;
import com.example.catalog.user.enums.UserStatusEnum;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetailsImpl implements UserDetails.
 * It holds information about current user.
 */
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

  private User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<GrantedAuthority> authorities = new HashSet<>();
    String role = user.getRole().toString();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

    return authorities;
  }

  public String getCurrentUserRole() {

    return this.user.getRole().toString();
  }

  @Override
  public String getPassword() {

    return user.getPassword();
  }

  @Override
  public String getUsername() {

    return user.getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {

    return true;
  }

  @Override
  public boolean isAccountNonLocked() {

    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {

    return user.getStatus().equals(UserStatusEnum.ENABLED);
  }
}
