package my.eshop.repositories;

import my.eshop.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    @Query("SELECT u FROM Item u WHERE u.isDeleted = false AND u.id = ?1")
    Item findByID(UUID id);

    @Override
    @Query("SELECT u FROM Item u WHERE u.isDeleted = false")
    Page<Item> findAll(Pageable pageable);

}
