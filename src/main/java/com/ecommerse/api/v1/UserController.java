package com.ecommerse.api.v1;


import com.ecommerse.common.ApiResponse;
import com.ecommerse.model.SignInDto;
import com.ecommerse.model.SignInResponse;
import com.ecommerse.model.SignUpDto;
import com.ecommerse.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ApiResponse signUp(@RequestBody SignUpDto signUpDto){
        return userService.signUp(signUpDto);
    }
    @PostMapping("/signin")
    public SignInResponse signIn(@RequestBody SignInDto sign){
        return userService.signIn(sign);
    }
}
