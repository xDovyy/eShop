package my.eshop.controllers;

import my.eshop.converters.ItemConverter;
import my.eshop.dtos.ItemDTO;
import my.eshop.entities.Item;
import my.eshop.entities.User;
import my.eshop.enumerators.Role;
import my.eshop.services.ItemService;
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
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ItemDTO> createItem(@RequestBody ItemDTO itemDTO) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(itemService.createItem(user, itemDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(itemDTO);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(ItemConverter.itemToItemDTO(this.itemService.getItemById(id)));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems(@RequestParam(value = "category", required = false) String category, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(this.itemService.getItems(category, pageable));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ItemDTO>> getUserItems(@RequestParam(value = "id", required = false) UUID userId){
        try{
            if (userId != null) return ResponseEntity.status(HttpStatus.FOUND).body(ItemConverter.itemListToItemDTOList(userService.getUserById(userId).getItems()));
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.getUserByEmail(auth.getName());
            List<Item> itemList = user.getItems();
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(ItemConverter.itemListToItemDTOList(itemList));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping()
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ItemDTO> updateItem(@RequestBody ItemDTO itemDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            if (user.getRole() != Role.ADMIN || !itemService.checkIfSeller(user.getId(), itemDTO.getId())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(itemDTO);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(itemService.updateItem(itemDTO));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(itemDTO);
        }
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<ItemDTO> deleteItem(@RequestBody ItemDTO itemDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(auth.getName());
        try {
            if (user.getRole() != Role.ADMIN || !itemService.checkIfSeller(user.getId(), itemDTO.getId())){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(itemDTO);
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(itemService.deleteItem(itemDTO.getId()));
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
