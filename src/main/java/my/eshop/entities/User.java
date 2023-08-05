package my.eshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import my.eshop.enumerators.Role;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id()
    @GeneratedValue()
    private UUID id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column
    private String address;
    @Column
    private BigInteger phone;
    @Column(nullable = false)
    private Boolean isDeleted = false;
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Item> items;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orders;
    @Enumerated(EnumType.STRING)
    private Role role;
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
