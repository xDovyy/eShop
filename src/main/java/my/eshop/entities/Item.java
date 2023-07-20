package my.eshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "items")
public class Item {

    @Id()
    @GeneratedValue()
    private UUID id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Float price;
    @Column
    private String description;
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne
    private User seller;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Order> orders;
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
