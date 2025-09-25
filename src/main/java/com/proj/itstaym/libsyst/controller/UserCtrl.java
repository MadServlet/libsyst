package com.proj.itstaym.libsyst.controller;

import com.proj.itstaym.libsyst.controller.records.UserRecord;
import com.proj.itstaym.libsyst.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserCtrl {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(params = "id")
    public UserRecord getUser(@RequestParam Integer id) {
        return null;
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

        return null;
    }

}
