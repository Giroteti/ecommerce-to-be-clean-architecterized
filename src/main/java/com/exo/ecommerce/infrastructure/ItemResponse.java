package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.item.Item;

public class ItemResponse {
    private Long id;
    private String name;
    private String description;
    private Integer remainingInStock;
    private String price;

    public ItemResponse(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.remainingInStock = item.getRemainingInStock();
        this.price = CurrencyFormatter.formatCurrency(item.getPrice());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getRemainingInStock() {
        return remainingInStock;
    }

    public String getPrice() {
        return price;
    }
}
