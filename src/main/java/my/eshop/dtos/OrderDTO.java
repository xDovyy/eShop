package my.eshop.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDTO {

    private UUID id;
    private Integer quantity;

}
