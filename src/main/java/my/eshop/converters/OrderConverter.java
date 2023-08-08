package my.eshop.converters;

import my.eshop.dtos.FullOrderDTO;
import my.eshop.dtos.OrderDTO;
import my.eshop.entities.Item;
import my.eshop.entities.Order;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {

    public static OrderDTO orderToOrderDTO(Order order){
        if (order == null) throw new IllegalArgumentException();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setQuantity(orderDTO.getQuantity());
        return orderDTO;
    }

    public static Order orderDTOtoOrder(OrderDTO orderDTO){
        if (orderDTO == null || orderDTO.getQuantity() == null) throw new IllegalArgumentException();
        Order order = new Order();
        order.setQuantity(orderDTO.getQuantity());
        return order;
    }

    public static List<OrderDTO> orderListToOrderDTOList(List<Order> orderList){
        if (orderList != null){
            List<OrderDTO> orderDTOList = new ArrayList<>();
            for (Order order:orderList){
                orderDTOList.add(orderToOrderDTO(order));
            }
            return orderDTOList;
        }
        throw new IllegalArgumentException();
    }

    public static List<OrderDTO> orderListToOrderDTOList(Page<Order> orderPage) {
        List<OrderDTO> orderDTOList = null;
        if (orderPage != null && !orderPage.isEmpty()) {
            orderDTOList = new ArrayList<>();
            for (Order order : orderPage) {
                orderDTOList.add(orderToOrderDTO(order));
            }
        }
        return orderDTOList;
    }

    public static FullOrderDTO orderToFullOrderDTO(Order order){
        if (order != null){
            FullOrderDTO fullOrderDTO = new FullOrderDTO();
            fullOrderDTO.setId(order.getId());
            fullOrderDTO.setQuantity(order.getQuantity());
            Item item = order.getItem();
            fullOrderDTO.setItemId(item.getId());
            fullOrderDTO.setItemName(item.getName());
            fullOrderDTO.setItemPrice(item.getPrice());
            fullOrderDTO.setBuyerId(order.getUser().getId());
            fullOrderDTO.setSellerId(item.getSeller().getId());
            return fullOrderDTO;
        }
        throw new IllegalArgumentException();
    }

    public static List<FullOrderDTO> orderToFullOrderDTOList(List<Order> orders){
        if (orders != null){
            List<FullOrderDTO> fullOrderDTOList = new ArrayList<>();
            for (Order order: orders){
                fullOrderDTOList.add(orderToFullOrderDTO(order));
            }
            return fullOrderDTOList;
        }
        throw new IllegalArgumentException();
    }

}
