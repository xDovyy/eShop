package my.eshop.converters;

import my.eshop.dtos.OrderDTO;
import my.eshop.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderConverter {

    public static OrderDTO orderToOrderDTO(Order order){
        if (order == null) throw new IllegalArgumentException();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIsCompleted(order.getIsCompleted());
        orderDTO.setQuantity(order.getQuantity());
        return orderDTO;
    }

    public static Order orderDTOtoOrder(OrderDTO orderDTO){
        if (orderDTO == null || orderDTO.getQuantity() == null || orderDTO.getIsCompleted() == null) throw new IllegalArgumentException();
        Order order = new Order();
        order.setQuantity(orderDTO.getQuantity());
        order.setIsCompleted(orderDTO.getIsCompleted());
        return order;
    }

    public static List<OrderDTO> orderListToOrderDTOList(List<Order> orderList){
        if (orderList != null && !orderList.isEmpty()){
            List<OrderDTO> orderDTOList = new ArrayList<>();
            for (Order order:orderList){
                OrderDTO orderDTO = new OrderDTO();
                orderDTO.setIsCompleted(order.getIsCompleted());
                orderDTO.setQuantity(order.getQuantity());
                orderDTOList.add(orderDTO);
            }
            return orderDTOList;
        }
        throw new IllegalArgumentException();
    }

}
