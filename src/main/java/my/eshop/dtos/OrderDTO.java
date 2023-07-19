package my.eshop.dtos;

import lombok.Data;

@Data
public class OrderDTO {

    private UserDTO buyer;
    private ItemDTO item;
    private Integer quantity;
    private Boolean isCompleted;

}