package my.eshop.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDTO {

    private UUID id;
    private String email;
    private String name;
    private String surname;

}
