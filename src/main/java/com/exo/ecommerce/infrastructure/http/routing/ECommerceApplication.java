package com.exo.ecommerce.infrastructure.http.routing;

import com.exo.ecommerce.infrastructure.bdd.cart.MySQLCartRepository;
import com.exo.ecommerce.infrastructure.bdd.invoice.MySQLInvoiceRepository;
import com.exo.ecommerce.infrastructure.bdd.item.MySQLItemRepository;
import com.exo.ecommerce.usecases.additemtocart.AddItemToCart;
import com.exo.ecommerce.usecases.checkout.CheckOut;
import com.exo.ecommerce.usecases.getallinvoices.GetAllInvoices;
import com.exo.ecommerce.usecases.getallitems.GetAllItems;
import com.exo.ecommerce.usecases.getcurrentcart.GetCurrentCart;
import com.exo.ecommerce.usecases.getinvoice.GetInvoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan("com.exo.ecommerce")
@EnableJpaRepositories(basePackages = {"com.exo.ecommerce.infrastructure.bdd"})
@EntityScan("com.exo.ecommerce")
@SpringBootApplication
public class ECommerceApplication extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(ECommerceApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ECommerceApplication.class);
    }

    @Bean
    public CheckOut provideCheckOutUseCase(
            MySQLCartRepository cartRepository,
            MySQLInvoiceRepository invoiceRepository
    ) {
        return new CheckOut(
                cartRepository,
                invoiceRepository
        );
    }

    @Bean
    public AddItemToCart provideAddItemToCartUseCase(
           MySQLItemRepository itemRepository,
           MySQLCartRepository cartRepository

    ) {
        return new AddItemToCart(
                itemRepository,
                cartRepository
        );
    }

    @Bean
    public GetCurrentCart provideGetCurrentCartUseCase(
            MySQLCartRepository cartRepository
    ) {
        return new GetCurrentCart(
                cartRepository
        );
    }

    @Bean
    public GetAllInvoices provideGetAllInvoicesUseCase(
        MySQLInvoiceRepository invoiceRepository
    ) {
        return new GetAllInvoices(
                invoiceRepository
        );
    }

    @Bean
    public GetInvoice provideGetInvoiceUseCase(
            MySQLInvoiceRepository invoiceRepository
    ) {
        return new GetInvoice(
            invoiceRepository
        );
    }

    @Bean
    public GetAllItems provideGetAllItemsUseCase(
           MySQLItemRepository itemRepository
    ) {
        return new GetAllItems(
            itemRepository
        );
    }

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}

