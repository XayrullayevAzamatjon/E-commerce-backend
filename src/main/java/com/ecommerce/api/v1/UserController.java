package com.ecommerce.api.v1;


import com.ecommerce.common.ApiResponse;
import com.ecommerce.model.SignInDto;
import com.ecommerce.model.SignInResponse;
import com.ecommerce.model.SignUpDto;
import com.ecommerce.service.UserService;
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
