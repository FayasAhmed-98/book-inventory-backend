package com.example.book_inventory.security;

import com.example.book_inventory.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert the role into a GrantedAuthority for Spring Security
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // You can implement additional checks here if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // You can implement additional checks here if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // You can implement additional checks here if needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // You can implement additional checks here if needed
    }

    // You can add custom getters for user properties if needed
    public String getRole() {
        return user.getRole().name();
    }
}
