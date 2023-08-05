package my.eshop.dtos;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SellerDTO extends UserDTO{

    private String address;
    private BigInteger phone;

}
