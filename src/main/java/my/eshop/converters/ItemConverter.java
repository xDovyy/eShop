package my.eshop.converters;

import my.eshop.dtos.ItemDTO;
import my.eshop.dtos.OrderDTO;
import my.eshop.entities.Item;
import my.eshop.entities.Order;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class ItemConverter {

    public static ItemDTO itemToItemDTO(Item item){
        if (item == null) throw new IllegalArgumentException();
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName(item.getName());
        itemDTO.setPrice(item.getPrice());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setQuantity(item.getQuantity());
        return itemDTO;
    }

    public static Item itemDTOToItem(ItemDTO itemDTO){
        if (itemDTO == null || itemDTO.getDescription() == null || itemDTO.getName() == null ||
                itemDTO.getPrice() == null || itemDTO.getQuantity() == null) throw new IllegalArgumentException();
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        return item;
    }

    public static List<ItemDTO> itemListToItemDTOList(List<Item> itemList){
        if (itemList != null && !itemList.isEmpty()){
            List<ItemDTO> itemDTOList = new ArrayList<>();
            for (Item item:itemList){
                ItemDTO itemDTO = new ItemDTO();
                itemDTO.setName(item.getName());
                itemDTO.setPrice(item.getPrice());
                itemDTO.setDescription(item.getDescription());
                itemDTO.setQuantity(item.getQuantity());
                itemDTOList.add(itemDTO);
            }
            return itemDTOList;
        }
        throw new IllegalArgumentException();
    }

    public static List<ItemDTO> itemListToItemDTOList(Page<Item> itemPage) {
        List<ItemDTO> itemDTOList = null;
        if (itemPage != null && !itemPage.isEmpty()) {
            itemDTOList = new ArrayList<>();
            for (Item item : itemPage) {
                itemDTOList.add(itemToItemDTO(item));
            }
        }
        return itemDTOList;
    }

}
