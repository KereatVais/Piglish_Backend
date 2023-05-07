package com.example.AuthMicroservice.util;

import com.example.AuthMicroservice.models.User;
import com.example.AuthMicroservice.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserDetailsService personDetailsService;

    @Autowired
    public UserValidator(UserDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User person = (User) target;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignored) {
            return; //все ок, пользователь не найден
        }

        errors.rejectValue("username", "", "Человек с таким именем пользователя уже существует!");
    }
}
