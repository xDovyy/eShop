package my.eshop.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class FullOrderDTO extends OrderDTO{

    private UUID itemId;
    private String itemName;
    private Float itemPrice;
    private String description;
    private UUID sellerId;
    private UUID buyerId;

}
