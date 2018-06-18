package com.exo.ecommerce.infrastructure.bdd.cart;

import com.exo.ecommerce.infrastructure.bdd.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany
    private List<Item> items;
    private Boolean checkedOut;

    public Cart(Long id, List<Item> items, Boolean checkedOut) {
        this.id = id;
        this.items = items;
        this.checkedOut = checkedOut;
    }

    public Cart() {
        this.id = null;
        this.items = new ArrayList<>();
        checkedOut = false;
    }

    public com.exo.ecommerce.domain.cart.Cart toDomainEntity() {
        List<com.exo.ecommerce.domain.item.Item> domainItems = items.stream()
                .map(Item::toDomainEntity)
                .collect(toList());

        return new com.exo.ecommerce.domain.cart.Cart(
                this.id,
                domainItems,
                this.checkedOut
        );
    }

    public static Cart fromDomainEntity(com.exo.ecommerce.domain.cart.Cart cart) {
        List<Item> items = cart.getItems().stream()
                .map(Item::fromDomainEntity)
                .collect(toList());

        return new Cart(
                cart.getId(),
                items,
                cart.getCheckedOut()
        );
    }

    public Long getId() {
        return id;
    }
}
