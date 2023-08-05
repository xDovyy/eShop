package my.eshop.repositories;

import my.eshop.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.id = ?1")
    User findByID(UUID id);

    @Override
    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    Page<User> findAll(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.isDeleted = false AND u.email = ?1")
    User findByEmail(String email);

    @Query(
            value = "SELECT u FROM User u WHERE u.isDeleted = false AND u.name LIKE ?1 ORDER BY u.id",
            countQuery = "SELECT count(*) FROM User")

    Page<User> findAllByNameLike(String name, Pageable pageable);

}
