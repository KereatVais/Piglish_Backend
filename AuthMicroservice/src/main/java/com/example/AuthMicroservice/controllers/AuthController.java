package com.example.AuthMicroservice.controllers;

import com.example.AuthMicroservice.models.RefreshToken;
import com.example.AuthMicroservice.models.User;
import com.example.AuthMicroservice.requests.TokenLoginRequest;
import com.example.AuthMicroservice.requests.TokenRefreshRequest;
import com.example.AuthMicroservice.responses.JwtResponse;
import com.example.AuthMicroservice.responses.TokenRefreshResponse;
import com.example.AuthMicroservice.security.JWTUtil;
import com.example.AuthMicroservice.security.UserDetailsImpl;
import com.example.AuthMicroservice.services.RefreshTokenService;
import com.example.AuthMicroservice.services.RegistrationService;
import com.example.AuthMicroservice.dto.AuthenticationDTO;
import com.example.AuthMicroservice.dto.UserDTO;
import com.example.AuthMicroservice.services.UserService;
import com.example.AuthMicroservice.util.TokenRefreshException;
import com.example.AuthMicroservice.util.UserNotCreatedException;
import com.example.AuthMicroservice.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final RefreshTokenService refreshTokenService;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserValidator personValidator, RefreshTokenService refreshTokenService, RegistrationService registrationService, JWTUtil jwtUtil, UserService userService, AuthenticationManager authenticationManager) {
        this.userValidator = personValidator;
        this.refreshTokenService = refreshTokenService;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody @Valid UserDTO userDTO,
                                                   BindingResult bindingResult) {
        User user = userService.convertToUser(userDTO);

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getDefaultMessage());
            }

            throw new UserNotCreatedException(errorMsg.toString());
        }

        registrationService.register(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtil.generateTokenFromUsername(user.getUsername());
                    Date expirationDate = jwtUtil.validateTokenAndRetrieveExpirationDate(token);
                    return ResponseEntity.ok(new TokenRefreshResponse(token, expirationDate, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Данный refresh-token отсутствует в базе данных"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильные логин и пароль");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(userDetails);

        Date expirationDate = jwtUtil.validateTokenAndRetrieveExpirationDate(jwt);

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, expirationDate, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), roles));
    }

    @PostMapping("/login-by-token")
    public ResponseEntity<?> LoginByToken(@RequestBody TokenLoginRequest tokenLoginRequest) {
        String username = jwtUtil.validateTokenAndRetrieveUsername(tokenLoginRequest.getToken());
        UserDTO user = userService.findUserByUsername(username);

        return ResponseEntity.ok(Map.of("username", username,
                "id", user.getId(),
                "dateOfBirth", user.getDateOfBirth(),
                "country", user.getCountry(),
                "email", user.getEmail()
        ));
    }

    @ExceptionHandler
    private ResponseEntity<String> handleBadCredentialsExceptionException(BadCredentialsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<String> handleUserNotCreatedException(UserNotCreatedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
