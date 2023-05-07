package com.example.AuthMicroservice.services;

import com.example.AuthMicroservice.models.User;
import com.example.AuthMicroservice.repositories.UsersRepository;
import com.example.AuthMicroservice.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден!");

        return new UserDetailsImpl(user.get());
    }
}
