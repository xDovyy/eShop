package my.eshop.converters;

import my.eshop.dtos.UserDTO;
import my.eshop.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserConverter {

    public static UserDTO userTouserDTO(User user){
        if (user == null) throw new IllegalArgumentException();
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setSurname(user.getSurname());
        return userDTO;
    }

    public static User userDTOToUser(UserDTO userDTO){
        if (userDTO == null || userDTO.getEmail() == null || userDTO.getName() == null || userDTO.getSurname() == null) throw new IllegalArgumentException();
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setSurname(userDTO.getSurname());
        return user;
    }

    public static List<UserDTO> userListTouserDTOList(List<User> userList){
        if (userList != null && !userList.isEmpty()){
            List<UserDTO> userDTOList = new ArrayList<>();
            for (User user:userList){
                UserDTO userDTO = new UserDTO();
                userDTO.setName(user.getName());
                userDTO.setEmail(user.getEmail());
                userDTO.setSurname(user.getSurname());
                userDTOList.add(userDTO);
            }
            return userDTOList;
        }
        throw new IllegalArgumentException();
    }

}
