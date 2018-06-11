package com.exo.ecommerce.domain.cart;

import com.exo.ecommerce.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long id;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
