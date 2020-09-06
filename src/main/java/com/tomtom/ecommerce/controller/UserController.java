package com.tomtom.ecommerce.controller;

import com.tomtom.ecommerce.bean.person.User;
import com.tomtom.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> response = userService.getAllUsers();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in processing the request to get all user info" + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        try {
            User user = userService.getUser(userId);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Exception in getting the user info for id : " + userId + " IS :: " + e);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping
    public ResponseEntity<String> addNewUser(@RequestBody @Valid User user) {
        try {
            userService.saveOrUpdateUser(user);
            return new ResponseEntity<>("Success ..!!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Exception in saving the user ::" + user + " is : " + e);
        }
        return new ResponseEntity<>("Unable to store user", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
