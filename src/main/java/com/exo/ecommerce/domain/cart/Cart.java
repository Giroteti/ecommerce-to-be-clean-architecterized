package com.exo.ecommerce.domain.cart;

import com.exo.ecommerce.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long id;
    private List<Item> items;
    private Boolean checkedOut;

    public Cart() {
        items = new ArrayList<>();
        checkedOut = false;
    }

    public Cart(Long id, List<Item> items, Boolean checkedOut) {
        this.id = id;
        this.items = items;
        this.checkedOut = checkedOut;
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

    public double calculateTotalPrice() {
        return this.items.stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
