package my.eshop.repositories;

import my.eshop.entities.Item;
import my.eshop.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT u FROM Order u WHERE u.isDeleted = false AND u.id = ?1")
    Order findByID(UUID id);

    @Override
    @Query("SELECT u FROM Order u WHERE u.isDeleted = false")
    Page<Order> findAll(Pageable pageable);

    @Query("SELECT u FROM Order u WHERE u.item = ?1 AND u.isDeleted = false")
    List<Order> findByItem(Item item);


}
