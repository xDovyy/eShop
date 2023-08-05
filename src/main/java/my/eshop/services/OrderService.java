package my.eshop.services;

import lombok.AllArgsConstructor;
import my.eshop.converters.OrderConverter;
import my.eshop.dtos.OrderDTO;
import my.eshop.entities.Item;
import my.eshop.entities.Order;
import my.eshop.entities.User;
import my.eshop.enumerators.OrderStatus;
import my.eshop.exceptions.OrderIsLockedException;
import my.eshop.repositories.OrderRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    public OrderDTO createOrder(User user, Item item, OrderDTO orderDTO){
        if (item.getQuantity() < orderDTO.getQuantity()) throw new IllegalArgumentException();
        for (Order order: user.getOrders()){
            if (order.getItem().equals(item) && order.getStatus() == OrderStatus.ADDED) orderRepository.delete(order);
        }
        Order order = OrderConverter.orderDTOtoOrder(orderDTO);
        order.setUser(user);
        item.setQuantity(item.getQuantity() - orderDTO.getQuantity());
        order.setItem(item);
        order.setTotalPrice(orderDTO.getQuantity() * item.getPrice());
        orderRepository.saveAndFlush(order);
        orderDTO.setId(order.getId());
        return orderDTO;
    }

    public Order getOrderById(UUID id){
        if (id != null) return orderRepository.findByID(id);
        throw new IllegalArgumentException();
    }

    public List<OrderDTO> getAllOrders(Pageable pageable){
        if (pageable != null) {
            return OrderConverter.orderListToOrderDTOList(orderRepository.findAll(pageable));
        }
        return OrderConverter.orderListToOrderDTOList(orderRepository.findAll());
    }

    public OrderDTO updateOrder(UUID id, OrderDTO orderDTO){
        Order order = getOrderById(id);
        if(order.getStatus() != OrderStatus.ADDED) throw new OrderIsLockedException();
        if (orderDTO.getQuantity() != null) {
            if (order.getItem().getQuantity() < orderDTO.getQuantity() - order.getQuantity()) throw new IllegalArgumentException();
            else {
                Item item = order.getItem();
                item.setQuantity(item.getQuantity() - (orderDTO.getQuantity() - order.getQuantity()));
                order.setItem(item);
                order.setQuantity(orderDTO.getQuantity());
                order.setTotalPrice(orderDTO.getQuantity() * item.getPrice());
                orderRepository.saveAndFlush(order);
            }
            return orderDTO;
        }
        else throw new IllegalArgumentException();
    }

    public OrderDTO deleteOrder(UUID id){
        Order order = getOrderById(id);
        if (order.getStatus() == OrderStatus.SENT || order.getStatus() == OrderStatus.DELIVERED) throw new OrderIsLockedException();
        order.setIsDeleted(true);
        orderRepository.saveAndFlush(order);
        return OrderConverter.orderToOrderDTO(order);
    }

    public void orderOrder(UUID id, String address){
        Order order = getOrderById(id);
        if (address == null) throw new IllegalArgumentException();
        order.setAddress(address);
        if (order.getStatus() == OrderStatus.ADDED) order.setStatus(OrderStatus.ORDERED);
        else throw new OrderIsLockedException();
        orderRepository.saveAndFlush(order);
    }

    public void payOrder(UUID id){
        Order order = getOrderById(id);
        if (order.getStatus() == OrderStatus.ORDERED) order.setStatus(OrderStatus.PAYED);
        else throw new OrderIsLockedException();
        orderRepository.saveAndFlush(order);
    }

    public void sendOrder(UUID id){
        Order order = getOrderById(id);
        if (order.getStatus() == OrderStatus.PAYED) order.setStatus(OrderStatus.SENT);
        else throw new OrderIsLockedException();
        orderRepository.saveAndFlush(order);
    }

    public void deliverOrder(UUID id){
        Order order = getOrderById(id);
        if (order.getStatus() == OrderStatus.SENT) order.setStatus(OrderStatus.DELIVERED);
        else throw new OrderIsLockedException();
        orderRepository.saveAndFlush(order);
    }

}
