package com.exo.ecommerce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class ECommerceController {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    InvoiceRepository invoiceRepository;

    @RequestMapping(path = "/items", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<Item> allItems() {
        return (ArrayList<Item>) itemRepository.findAll();
    }

    @RequestMapping(path = "/add-item", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public AddItemResponse addItemToCart(@RequestParam("id") Long id) {
        Optional<Item> purchasedItem = itemRepository.findById(id);

        if (purchasedItem.isPresent() && purchasedItem.get().getRemainingInStock() > 0) {
            Item item = purchasedItem.get();
            Optional<Cart> currentCart = cartRepository.findTopByCheckedOutOrderByIdDesc(false);
            Cart cart = null;
            if (currentCart.isPresent()) {
                cart = currentCart.get();
            } else {
                cart = new Cart();
            }
            item.decrementRemainingInStock();
            item = itemRepository.save(item);
            cart.addItem(item);
            cartRepository.save(cart);
            return new AddItemResponse("Item " + id + " added successfully to the cart", cart);
        } else {
            return new AddItemResponse("Item " + id + " is not available");
        }
    }

    @RequestMapping(path = "/check-out", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CheckOutCartResponse checkOutCart() {
        Optional<Cart> currentCart = cartRepository.findTopByCheckedOutOrderByIdDesc(false);
        if (currentCart.isPresent()) {
            Cart cart = currentCart.get();
            Invoice invoice = new Invoice(cart);
            cart.setCheckedOut(true);
            invoiceRepository.save(invoice);
            cartRepository.save(cart);
            return new CheckOutCartResponse("Cart checked out successfully", invoice);
        } else {
            return new CheckOutCartResponse("Nothing to check out");
        }
    }

    @RequestMapping(path = "/cart", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CartResponse currentCart() {
        return new CartResponse(cartRepository.findTopByCheckedOutOrderByIdDesc(false).orElse(null));
    }

    @RequestMapping(path = "/invoices", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<InvoiceResponse> allInvoices() {
        ArrayList<Invoice> invoices = (ArrayList<Invoice>) invoiceRepository.findAll();
        ArrayList<InvoiceResponse> response = new ArrayList<InvoiceResponse>();
        for (Invoice invoice : invoices) {
            response.add(new InvoiceResponse(invoice));
        }
        return response;
    }

    @RequestMapping(path = "/invoice", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public InvoiceResponse invoice(@RequestParam("id") Long id) {
        return new InvoiceResponse(invoiceRepository.findById(id).orElse(null));
    }
}
