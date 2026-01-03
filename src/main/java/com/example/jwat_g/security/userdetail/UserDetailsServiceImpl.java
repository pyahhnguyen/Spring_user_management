package com.example.jwat_g.security.userdetail;

import com.example.jwat_g.mapper.AccountMapper;
import com.example.jwat_g.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountMapper accountMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountMapper.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("Account not found with email: " + email);
        }
        return UserDetailsImpl.build(account);
    }
}
