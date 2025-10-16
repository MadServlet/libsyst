package com.proj.itstaym.controller.api;

import com.proj.itstaym.controller.api.records.UserRecord;
import com.proj.itstaym.exception.user.UserNotFoundException;
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

    // Create
    @PostMapping("/save")
    public UserRecord createUser(@RequestBody UserRecord user) {
        return userService.createUser(user);
    }

    @PostMapping("/save/bulk")
    public List<UserRecord> createUser(@RequestBody List<UserRecord> users) {
        return userService.createUsers(users);
    }

    // Read
    @GetMapping(path = "/find", params = "id")
    public UserRecord find(@RequestParam BigInteger id) {
        return userService.find(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @GetMapping(path = "/find", params = "email")
    public UserRecord find(@RequestParam String email) {
        return userService.find(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    @GetMapping(path = "/find/bulk", params = {"page", "size"})
    public List<UserRecord> findAll(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return userService.findAll(page, size);
    }

    @PostMapping(path = "/find/bulk", params = {"page", "size"})
    public List<UserRecord> findAll(@RequestBody UserRecord userRecord, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return userService.findByCriteria(userRecord, page, size);
    }

    // Update
    @PatchMapping("/update")
    public UserRecord updateUser(@RequestBody UserRecord userRecord) {
        return userService.updateUser(userRecord);
    }

    @PatchMapping("/update/bulk")
    public List<UserRecord> updateUsers(@RequestBody List<UserRecord> userRecords) {
        return userService.updateUsers(userRecords);
    }

    // Delete
    @DeleteMapping(path = "/delete", params = "id")
    public void deleteUser(@RequestParam("id") BigInteger id) {
        userService.deleteUser(id);
    }

    @DeleteMapping(path = "/delete", params = "email")
    public void deleteUser(@RequestParam("email") String email) {
        userService.deleteUser(email);
    }

}
