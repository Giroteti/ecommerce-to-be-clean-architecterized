package com.exo.ecommerce.infrastructure.bdd.item;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    private Long id;
    private String name;
    private String description;
    private Integer remainingInStock;
    private Double price;

    public Item(Long id, String name, String description, Integer remainingInStock, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.remainingInStock = remainingInStock;
        this.price = price;
    }

    public Item() {
    }

    public com.exo.ecommerce.domain.item.Item toDomainEntity() {
        return new com.exo.ecommerce.domain.item.Item(
                this.id,
                this.name,
                this.description,
                this.remainingInStock,
                this.price
        );
    }

    public static Item fromDomainEntity(com.exo.ecommerce.domain.item.Item item) {
        return new Item(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getRemainingInStock(),
                item.getPrice()
        );
    }

}
