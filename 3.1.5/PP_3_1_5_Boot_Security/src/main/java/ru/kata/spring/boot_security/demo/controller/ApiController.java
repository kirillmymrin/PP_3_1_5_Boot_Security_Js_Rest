package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")

public class ApiController {
    @Autowired
    private final UserService userService;

    public ApiController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/currentUser")
    @ResponseBody
    public ResponseEntity<User> getAuthorizeUser(Principal principal) {
        return new ResponseEntity<>(userService.getUser(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.allUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id")Long id){
        return userService.getUser(id);
    }

    @PatchMapping("/users")
    public ResponseEntity<User> patchUser(@RequestBody User user, @RequestParam(required = false
            , name = "selectedRoles") String[] selectedRoles) {
        user.setRoles(userService.saveRole(selectedRoles));
        userService.update(user.getId(),user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user, @RequestParam(required = false
            , name = "selectedRoles") String[] selectedRoles) {
        user.setRoles(userService.saveRole(selectedRoles));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @DeleteMapping ("/users/{deleteId}")
    public void delete(@PathVariable(required = true, name = "deleteId") Long id) {
        userService.delete(id);
    }

}
