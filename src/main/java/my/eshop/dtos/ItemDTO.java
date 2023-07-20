package my.eshop.dtos;

import lombok.Data;

@Data
public class ItemDTO {

    private String name;
    private Float price;
    private String description;
    private Integer quantity;

}
