package my.eshop.controllers;

import my.eshop.converters.OrderConverter;
import my.eshop.dtos.CreateOrderDTO;
import my.eshop.dtos.FullOrderDTO;
import my.eshop.dtos.OrderDTO;
import my.eshop.entities.User;
import my.eshop.services.ItemService;
import my.eshop.services.OrderService;
import my.eshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/orders")
@PreAuthorize("hasAnyRole('USER')")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderDTO orderDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(orderService.createOrder(user, itemService.getItemById(orderDTO.getItemId()), orderDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(OrderConverter.orderToOrderDTO(this.orderService.getOrderById(id)));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders(Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(this.orderService.getAllOrders(pageable));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<FullOrderDTO>> getUserOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(this.orderService.getUserOrders(user));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/sell")
    @PreAuthorize("hasAnyRole('SELLER')")
    public ResponseEntity<List<FullOrderDTO>> getSellerOrders(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try{
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(this.orderService.getSellerOrders(user));
        }
        catch (IllegalArgumentException e){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable("id") UUID id, @RequestBody OrderDTO orderDTO){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(orderService.updateOrder(id, orderDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(orderDTO);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(orderService.deleteOrder(id));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/order/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<FullOrderDTO> orderOrder(@PathVariable("id") UUID id, @RequestBody String address){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(orderService.orderOrder(id, address));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
