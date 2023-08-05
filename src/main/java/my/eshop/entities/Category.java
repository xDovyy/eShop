package my.eshop.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Item> item;

}
