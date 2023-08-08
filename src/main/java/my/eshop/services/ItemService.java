package my.eshop.services;

import lombok.AllArgsConstructor;
import my.eshop.converters.ItemConverter;
import my.eshop.dtos.ItemDTO;
import my.eshop.entities.Category;
import my.eshop.entities.Item;
import my.eshop.entities.User;
import my.eshop.repositories.CategoryRepository;
import my.eshop.repositories.ItemRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ItemService {

    private ItemRepository itemRepository;
    private CategoryRepository categoryRepository;

    public ItemDTO createItem(User user, ItemDTO itemDTO){
        Item item = ItemConverter.itemDTOToItem(itemDTO);
        if (itemDTO.getCategory() == null) throw new IllegalArgumentException();
        itemDTO.setCategory(itemDTO.getCategory().toLowerCase());
        if (categoryRepository.findById(itemDTO.getCategory()).isPresent()){
            item.setCategory(categoryRepository.findById(itemDTO.getCategory()).orElse(null));
        }
        else {
            Category category = new Category();
            category.setName(itemDTO.getCategory().toLowerCase());
            this.categoryRepository.save(category);
            item.setCategory(category);
        }
        item.setSeller(user);
        this.categoryRepository.flush();
        this.itemRepository.saveAndFlush(item);
        return itemDTO;
    }

    public Item getItemById(UUID id){
        if (id == null) throw new IllegalArgumentException();
        return itemRepository.findByID(id);
    }

    public List<ItemDTO> getItems(String category, Pageable pageable){
        if (pageable != null) {
            if (category != null){
                return ItemConverter.itemListToItemDTOList(itemRepository.
                        findAllByCategory(categoryRepository.findById(category.toLowerCase()).orElse(null), pageable));
            }
            return ItemConverter.itemListToItemDTOList(itemRepository.findAll(pageable));
        }
        return ItemConverter.itemListToItemDTOList(itemRepository.findAll());
    }

    public ItemDTO updateItem(ItemDTO itemDTO){
        Item item = getItemById(itemDTO.getId());
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
        item.setIsDeleted(true);
        itemRepository.saveAndFlush(item);
        return ItemConverter.itemToItemDTO(item);
    }

    public boolean checkIfSeller(UUID id, UUID id1) {
        Item item = itemRepository.findById(id1).orElse(null);
        if (item == null) throw new NoSuchElementException();
        return item.getSeller().getId() == id;
    }
}
