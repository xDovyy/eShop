package my.eshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import my.eshop.enumerators.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id()
    @GeneratedValue()
    private UUID id;
    @Column(nullable = false)
    private Integer quantity;
    @Column
    private Float totalPrice;
    @Column
    private String address;
    @ManyToOne
    private User user;
    @ManyToOne
    private Item item;
    @Column(nullable = false)
    private Boolean isDeleted = false;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @PrePersist
    void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
