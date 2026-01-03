package com.example.jwat_g.security.userdetail;

import com.example.jwat_g.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(Account account) {
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + account.getRole());
        return new UserDetailsImpl(
                account.getId(),
                account.getEmail(),
                account.getPassword(),
                account.getEmail(),
                account.getFullName(),
                account.getRole(),
                Collections.singletonList(authority));
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
        return true;
    }
}
