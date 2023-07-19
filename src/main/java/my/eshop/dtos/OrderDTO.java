package my.eshop.dtos;

import lombok.Data;

@Data
public class OrderDTO {

    private Integer quantity;
    private Boolean isCompleted;

}
