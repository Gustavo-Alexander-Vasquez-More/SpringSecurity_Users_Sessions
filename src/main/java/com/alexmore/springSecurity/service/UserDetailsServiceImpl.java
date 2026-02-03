package com.alexmore.springSecurity.service;

import com.alexmore.springSecurity.model.User; // Asegúrate de que este sea el import de TU entidad
import com.alexmore.springSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos en la DB usando TU entidad
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // 2. Retornamos el User de Spring Security (usando la ruta completa para evitar conflictos)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                // 3. Aquí va el método que devuelve el String del rol (ej: getRole())
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}