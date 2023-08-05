package my.eshop.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ItemDTO {

    private UUID id;
    private String name;
    private Float price;
    private String description;
    private Integer quantity;
    private String category;

}
