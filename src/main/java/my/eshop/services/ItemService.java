package my.eshop.services;

import my.eshop.converters.ItemConverter;
import my.eshop.dtos.ItemDTO;
import my.eshop.entities.Item;
import my.eshop.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public void createItem(ItemDTO itemDTO){
        Item item = ItemConverter.itemDTOToItem(itemDTO);
        itemRepository.saveAndFlush(item);
    }

    public Item getItemById(String id){
        if (id == null) throw new IllegalArgumentException();
        return itemRepository.findById(id).orElse(null);
    }

    public void updateItem(String id, ItemDTO itemDTO){
        Item item = getItemById(id);
        if (item == null) throw new NoSuchElementException();
        if (itemDTO.getQuantity() != null) item.setQuantity(itemDTO.getQuantity());
        if (itemDTO.getPrice() != null) item.setPrice(itemDTO.getPrice());
        if (itemDTO.getDescription() != null) item.setDescription(itemDTO.getDescription());
        if (itemDTO.getName() != null) item.setName(itemDTO.getName());
        itemRepository.saveAndFlush(item);
    }

    public void deleteItem(String id){
        Item item = getItemById(id);
        if (item == null) throw new NoSuchElementException();
        itemRepository.delete(item);
    }

}
