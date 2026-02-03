package com.alexmore.springSecurity.repository;

import com.alexmore.springSecurity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Buscar si existe un usuario por su nombre de usuario
    boolean existsByUsername(String username);
    //Buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);

}
