package com.romel.blogapp.servicesss;

import com.romel.blogapp.mainStuff.Account;
import com.romel.blogapp.mainStuff.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountService.findByEmail(email);
        if(!account.isPresent()){
            throw new UsernameNotFoundException("Account not found");
        }

        Account account1 = account.get();
        List<GrantedAuthority> grantedAuthorities = account1
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new User(account1.getEmail(), account1.getPassword(), grantedAuthorities);
    }
}
