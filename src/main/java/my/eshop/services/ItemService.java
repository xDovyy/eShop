package my.eshop.services;

import my.eshop.converters.ItemConverter;
import my.eshop.converters.OrderConverter;
import my.eshop.dtos.ItemDTO;
import my.eshop.entities.Item;
import my.eshop.entities.User;
import my.eshop.repositories.ItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemDTO createItem(User user, ItemDTO itemDTO){
        Item item = ItemConverter.itemDTOToItem(itemDTO);
        item.setSeller(user);
        itemRepository.saveAndFlush(item);
        return itemDTO;
    }

    public Item getItemById(UUID id){
        if (id == null) throw new IllegalArgumentException();
        return itemRepository.findById(id).orElse(null);
    }

    public List<ItemDTO> getItems(Pageable pageable){
        if (pageable != null) {
            return ItemConverter.itemListToItemDTOList(itemRepository.findAll(pageable));
        }
        return ItemConverter.itemListToItemDTOList(itemRepository.findAll());
    }

    public ItemDTO updateItem(UUID id, ItemDTO itemDTO){
        Item item = getItemById(id);
        if (item == null) throw new NoSuchElementException();
        if (itemDTO.getQuantity() != null) item.setQuantity(itemDTO.getQuantity());
        if (itemDTO.getPrice() != null) item.setPrice(itemDTO.getPrice());
        if (itemDTO.getDescription() != null) item.setDescription(itemDTO.getDescription());
        if (itemDTO.getName() != null) item.setName(itemDTO.getName());
        itemRepository.saveAndFlush(item);
        return itemDTO;
    }

    public ItemDTO deleteItem(UUID id){
        Item item = getItemById(id);
        if (item == null) throw new NoSuchElementException();
        itemRepository.delete(item);
        return ItemConverter.itemToItemDTO(item);
    }

}
