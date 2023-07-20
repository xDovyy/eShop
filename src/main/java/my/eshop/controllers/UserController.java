package my.eshop.controllers;

import my.eshop.converters.UserConverter;
import my.eshop.dtos.CreateUserDTO;
import my.eshop.dtos.UserDTO;
import my.eshop.entities.User;
import my.eshop.exceptions.CheckFailedException;
import my.eshop.exceptions.DuplicateEmailException;
import my.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/users")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.createUser(createUserDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createUserDTO);
        }
        catch (DuplicateEmailException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createUserDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(UserConverter.userTouserDTO(this.userService.getUserById(id)));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(this.userService.getAllUsers(pageable));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") UUID id, @RequestBody UserDTO userDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(userService.updateUser(id, userDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDTO);
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(userService.updateUser(user.getId(), userDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(userService.deleteUser(id));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDTO> deleteUser(@RequestBody String password){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(userService.deleteUserWithPasswordCheck(password, user));
        }
        catch (CheckFailedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

}
