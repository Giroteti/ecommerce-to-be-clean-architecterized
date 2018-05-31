package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.domain.Cart;
import com.exo.ecommerce.domain.Invoice;
import com.exo.ecommerce.domain.Item;
import com.exo.ecommerce.usecases.AddItemToCart;
import com.exo.ecommerce.usecases.CheckOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class ECommerceController {

    @Autowired
    AddItemToCart addItemToCart;
    @Autowired
    AddItemPresenter addItemPresenter;
    @Autowired
    ItemCRUDRepository itemRepository;
    @Autowired
    CartCRUDRepository cartRepository;
    @Autowired
    InvoiceCRUDRepository invoiceRepository;

    @RequestMapping(path = "/items", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<Item> allItems() {
        return (ArrayList<Item>) itemRepository.findAll();
    }

    @RequestMapping(path = "/add-item", produces = "application/json; charset=UTF-8")
    public ResponseEntity addItemToCart(@RequestParam("id") Long itemId) {
        return addItemPresenter.present(addItemToCart.handle(itemId), itemId);
    }

    @RequestMapping(path = "/check-out", produces = "application/json; charset=UTF-8")
    public ResponseEntity checkOutCart() {

        CheckOut checkOut = new CheckOut(new MySQLCartRepository(cartRepository), new MySQLInvoiceRepository(invoiceRepository));
        Invoice invoice = checkOut.handle();

        if (invoice != null) {
            return ResponseEntity.ok(new CheckOutCartResponse("Cart checked out successfully", invoice));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @RequestMapping(path = "/cart", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CartResponse currentCart() {
        Cart cart = cartRepository.findTopByCheckedOutOrderByIdDesc(false).orElse(null);
        if (cart != null && !cart.getItems().isEmpty()) {
            return new CartResponse(cart);
        } else {
            return new CartResponse(null);
        }
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
    public ResponseEntity invoice(@RequestParam("id") Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElse(null);
        if (invoice != null) {
            return ResponseEntity.ok(new InvoiceResponse(invoice));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
