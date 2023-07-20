package my.eshop.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderDTO extends OrderDTO{
    private UUID itemId;
}
