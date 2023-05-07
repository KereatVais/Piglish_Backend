package com.example.AuthMicroservice.services;

import com.example.AuthMicroservice.dto.UserDTO;
import com.example.AuthMicroservice.models.User;
import com.example.AuthMicroservice.repositories.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UsersRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UsersRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserDTO findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException("Пользователь не найден!");

        return convertToUserDTO(user.get());
    }

    public User convertToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return this.modelMapper.map(user, UserDTO.class);
    }
}
