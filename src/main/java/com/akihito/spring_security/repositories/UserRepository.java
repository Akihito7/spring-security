package com.akihito.spring_security.repositories;

import com.akihito.spring_security.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;


public interface UserRepository extends JpaRepository<UserModel, UUID> {
    public UserDetails findByUsername(String username);
}
