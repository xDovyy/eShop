package my.eshop.services;

import lombok.AllArgsConstructor;
import my.eshop.converters.UserConverter;
import my.eshop.dtos.CreateUserDTO;
import my.eshop.dtos.UserDTO;
import my.eshop.entities.User;
import my.eshop.enumerators.Role;
import my.eshop.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder encoder;
    private UserRepository userRepository;

    public CreateUserDTO createUser(CreateUserDTO userDTO){
        if (!userDTO.getPassword().equals(userDTO.getPasswordCheck())) throw new IllegalArgumentException();
        User user = UserConverter.userDTOToUser(userDTO);
        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setRole(Role.USER);
        userRepository.saveAndFlush(user);
        return userDTO;
    }

    public User getUserById(String id){
        if (id == null) throw new IllegalArgumentException();
        return userRepository.findById(id).orElse(null);
    }

    public void updateUser(String id, UserDTO userDTO){
        User user = getUserById(id);
        if (user == null) throw new NoSuchElementException();
        if (userDTO.getSurname() != null) user.setSurname(userDTO.getSurname());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getName() != null) user.setName(userDTO.getName());
        userRepository.saveAndFlush(user);
    }

    public void deleteUser(String id){
        User user = getUserById(id);
        if (user == null) throw new NoSuchElementException();
        userRepository.delete(user);
    }

}
