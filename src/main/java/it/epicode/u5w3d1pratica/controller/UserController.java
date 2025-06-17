package it.epicode.u5w3d1pratica.controller;

import it.epicode.u5w3d1pratica.dto.UserDto;
import it.epicode.u5w3d1pratica.exception.NonTrovatoException;
import it.epicode.u5w3d1pratica.exception.ValidationException;
import it.epicode.u5w3d1pratica.model.User;
import it.epicode.u5w3d1pratica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) throws NonTrovatoException {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody @Validated UserDto userDto, BindingResult bindingResult)
            throws NonTrovatoException, ValidationException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getAllErrors().stream()
                            .map(e -> e.getDefaultMessage())
                            .reduce("", String::concat)
            );
        }

        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) throws NonTrovatoException {
        userService.deleteUser(id);
    }
}
