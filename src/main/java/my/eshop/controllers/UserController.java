package my.eshop.controllers;

import my.eshop.dtos.CreateUserDTO;
import my.eshop.dtos.UserDTO;
import my.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
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
    }

}
