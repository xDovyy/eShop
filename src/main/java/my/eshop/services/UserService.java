package my.eshop.services;

import lombok.AllArgsConstructor;
import my.eshop.converters.UserConverter;
import my.eshop.dtos.CreateUserDTO;
import my.eshop.dtos.UserDTO;
import my.eshop.entities.User;
import my.eshop.enumerators.Role;
import my.eshop.exceptions.CheckFailedException;
import my.eshop.exceptions.DuplicateEmailException;
import my.eshop.repositories.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
        try {
            userRepository.saveAndFlush(user);
        }
        catch (Exception e){
            throw new DuplicateEmailException();
        }
        return userDTO;
    }

    public User getUserById(UUID id){
        if (id == null) throw new IllegalArgumentException();
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO updateUser(UUID id, UserDTO userDTO){
        User user = getUserById(id);
        if (user == null) throw new NoSuchElementException();
        if (userDTO.getSurname() != null) user.setSurname(userDTO.getSurname());
        if (userDTO.getEmail() != null) user.setEmail(userDTO.getEmail());
        if (userDTO.getName() != null) user.setName(userDTO.getName());
        userRepository.saveAndFlush(user);
        return userDTO;
    }

    public UserDTO deleteUser(UUID id){
        User user = getUserById(id);
        if (user == null) throw new NoSuchElementException();
        userRepository.delete(user);
        return UserConverter.userTouserDTO(user);
    }

    public UserDTO deleteUserWithPasswordCheck(String password, User user){
        if (encoder.matches(password, user.getPassword())) return deleteUser(user.getId());
        else throw new CheckFailedException();
    }

    public List<UserDTO> getAllUsers(Pageable pageable){
        if (pageable != null) {
            return UserConverter.userListTouserDTOList(userRepository.findAll(pageable));
        }
        return UserConverter.userListTouserDTOList(userRepository.findAll());
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

}
