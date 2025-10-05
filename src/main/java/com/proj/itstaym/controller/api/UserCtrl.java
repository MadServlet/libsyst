package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserCtrl {

    private final UserService userService;

    @Autowired
    public UserCtrl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = "id")
    public UserRecord getUser(@RequestParam Integer id) {
        return userService.getUser(new BigInteger(String.valueOf(id)));
    }

    @GetMapping(params = "email")
    public UserRecord getUser(@RequestParam String email) {

        return null;
    }

    @GetMapping(params = {"page", "count"})
    public List<UserRecord> getUsers(@RequestParam Integer page, @RequestParam Integer count) {

        return null;
    }

    @PostMapping
    public UserRecord createUser(@RequestBody UserRecord user) {
        return userService.createUser(user);
    }

}
