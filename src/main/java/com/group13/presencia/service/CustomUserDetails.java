package com.group13.presencia.service;

import com.group13.presencia.entity.Student;
import com.group13.presencia.entity.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Teacher teacher) {
        this.email = teacher.getEmail();
        this.password = teacher.getPassword();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEACHER"));
    }

    public CustomUserDetails(Student student) {
        this.email = student.getEmail();
        this.password = student.getPassword();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_STUDENT"));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

