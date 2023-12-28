package com.ecommerse.service;

import com.ecommerse.common.ApiResponse;
import com.ecommerse.entity.AuthenticationTokenEntity;
import com.ecommerse.entity.UserEntity;
import com.ecommerse.exceptions.AuthenticationFailedException;
import com.ecommerse.exceptions.CustomException;
import com.ecommerse.model.SignInDto;
import com.ecommerse.model.SignInResponse;
import com.ecommerse.model.SignUpDto;
import com.ecommerse.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AuthenticationTokenService tokenService;

    public UserService(UserRepository userRepository, AuthenticationTokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Transactional
    public ApiResponse signUp(SignUpDto signUpDto) {
        try {
            if (Objects.nonNull(userRepository.findByEmail(signUpDto.email()))) {
                throw new CustomException("User with the provided email already exists!");
            }
            String encodedPassword = hashPassword(signUpDto.password());
            UserEntity user = new UserEntity(signUpDto.firstName(), signUpDto.lastName(), signUpDto.email(), encodedPassword);
            userRepository.save(user);

            final AuthenticationTokenEntity tokenEntity = new AuthenticationTokenEntity(user);
            tokenService.saveConfirmationToken(tokenEntity);

            LOGGER.info("User '{}' signed up successfully.", signUpDto.email());

            return new ApiResponse("Success", "User signed up successfully!");
        } catch (Exception e) {
            LOGGER.error("Error occurred during user signup: {}", e.getMessage(), e);
            throw new CustomException("Failed to sign up user. Please try again later.");
        }
    }

    // Implement a sign-in method
    public SignInResponse signIn(SignInDto sign) {
        try {
            UserEntity user = userRepository.findByEmail(sign.email());

            if (user == null) {
                throw new AuthenticationFailedException("User not found for the provided email!");
            }

            String hashedPassword = hashPassword(sign.password());

            // Check if the provided password matches the stored hashed password
            if (hashedPassword.equals(user.getPassword())) {
                LOGGER.info("User [{}] signed in successfully.", sign.email());
                AuthenticationTokenEntity token=tokenService.getToken(user);
                return new SignInResponse("Success",token.getToken() );
            } else {
                throw new AuthenticationFailedException("Incorrect password !");
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred during user sign-in: {}", e.getMessage(), e);
            throw new AuthenticationFailedException("Failed to sign in. Please check your credentials and try again.");
        }

    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
