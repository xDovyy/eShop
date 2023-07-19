package my.eshop.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CreateUserDTO extends UserDTO{
    private String password;
    private String passwordCheck;
}
