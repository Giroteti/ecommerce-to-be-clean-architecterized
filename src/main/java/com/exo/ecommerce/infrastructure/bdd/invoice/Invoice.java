package com.exo.ecommerce.infrastructure.bdd.invoice;

import com.exo.ecommerce.infrastructure.bdd.cart.Cart;

import javax.persistence.*;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String date;
    @OneToOne
    private Cart cart;

    public Invoice(Long id, String date, Cart cart) {
        this.id = id;
        this.date = date;
        this.cart = cart;
    }

    public Invoice() {
    }

    public com.exo.ecommerce.domain.invoice.Invoice toDomainEntity() {
        return new com.exo.ecommerce.domain.invoice.Invoice(
                this.id,
                this.date,
                this.cart.toDomainEntity()
        );
    }

    public static Invoice fromDomainEntity(com.exo.ecommerce.domain.invoice.Invoice invoice) {
        return new Invoice(
                invoice.getId(),
                invoice.getDate(),
                Cart.fromDomainEntity(invoice.getCart())
        );
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
