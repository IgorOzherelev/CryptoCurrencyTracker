package isteriagroup.cryptotracker.controllers;

import isteriagroup.cryptotracker.dtos.UserDto;
import isteriagroup.cryptotracker.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("email/{userEmail}")
    public UserDto getUser(@PathVariable String userEmail) {
        return userService.getUserByEmail(userEmail);
    }
}