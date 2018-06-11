package com.exo.ecommerce.infrastructure;

import com.exo.ecommerce.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    CheckOut checkOut;
    @Autowired
    CheckOutPresenter checkOutPresenter;

    @Autowired
    GetCurrentCart getCurrentCart;
    @Autowired
    CurrentCartPresenter currentCartPresenter;

    @Autowired
    GetAllInvoices getAllInvoices;
    @Autowired
    InvoicesPresenter invoicesPresenter;

    @Autowired
    GetInvoice getInvoice;
    @Autowired
    InvoicePresenter invoicePresenter;

    @Autowired
    GetAllItems getAllItems;
    @Autowired
    ItemsPresenter itemsPresenter;

    @RequestMapping(path = "/items", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<ItemResponse> allItems() {
        return itemsPresenter.present(getAllItems.handle());
    }

    @RequestMapping(path = "/add-item", produces = "application/json; charset=UTF-8")
    public ResponseEntity addItemToCart(@RequestParam("id") Long itemId) {
        return addItemPresenter.present(addItemToCart.handle(itemId), itemId);
    }

    @RequestMapping(path = "/check-out", produces = "application/json; charset=UTF-8")
    public ResponseEntity checkOutCart() {

        return checkOutPresenter.present(checkOut.handle());
    }

    @RequestMapping(path = "/cart", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CartResponse currentCart() {
        return currentCartPresenter.present(getCurrentCart.handle());
    }

    @RequestMapping(path = "/invoices", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<InvoiceResponse> allInvoices() {
        return invoicesPresenter.present(getAllInvoices.handle());
    }

    @RequestMapping(path = "/invoice", produces = "application/json; charset=UTF-8")
    public ResponseEntity invoice(@RequestParam("id") Long invoiceId) {
        return invoicePresenter.present(getInvoice.handle(invoiceId));
    }
}
