package com.exo.ecommerce;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ManyToMany
    private List<Item> items;
    private Boolean checkedOut;

    public Cart() {
        items = new ArrayList<Item>();
        checkedOut = false;
    }

    public Long getId() {
        return id;
    }

    public List<Item> getItems() {
        return new ArrayList<>(this.items);
    }

    public Boolean getCheckedOut() {
        return checkedOut;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void setCheckedOut(Boolean checkedOut) {
        this.checkedOut = checkedOut;
    }

    public float calculateTotalPrice() {
        float totalPrice = 0;
        for (Item item : this.items) {
            totalPrice += item.getPrice();
        }
        return totalPrice;
    }
}
