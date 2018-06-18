package com.exo.ecommerce.infrastructure.bdd.cart;

import com.exo.ecommerce.infrastructure.bdd.item.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
        this.items = new ArrayList<Item>();
        checkedOut = false;
    }

    public com.exo.ecommerce.domain.cart.Cart toDomainEntity() {
        ArrayList<com.exo.ecommerce.domain.item.Item> domainItems = new ArrayList<>();
        for (Item item : items) {
            domainItems.add(item.toDomainEntity());
        }
        return new com.exo.ecommerce.domain.cart.Cart(
                this.id,
                domainItems,
                this.checkedOut
        );
    }

    static public Cart fromDomainEntity(com.exo.ecommerce.domain.cart.Cart cart) {
        ArrayList<Item> items = new ArrayList<>();
        for (com.exo.ecommerce.domain.item.Item item : cart.getItems()) {
            items.add(Item.fromDomainEntity(item));
        }
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
