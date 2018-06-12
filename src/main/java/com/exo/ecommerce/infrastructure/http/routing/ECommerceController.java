package com.exo.ecommerce.infrastructure.http.routing;

import com.exo.ecommerce.infrastructure.http.presentation.CartResponse;
import com.exo.ecommerce.infrastructure.http.presentation.InvoiceResponse;
import com.exo.ecommerce.infrastructure.http.presentation.additemtocart.AddItemToCartPresenter;
import com.exo.ecommerce.infrastructure.http.presentation.checkout.CheckOutPresenter;
import com.exo.ecommerce.infrastructure.http.presentation.getallinvoices.InvoicesPresenter;
import com.exo.ecommerce.infrastructure.http.presentation.getallitems.ItemResponse;
import com.exo.ecommerce.infrastructure.http.presentation.getallitems.ItemsPresenter;
import com.exo.ecommerce.infrastructure.http.presentation.getcurrentcart.CurrentCartPresenter;
import com.exo.ecommerce.infrastructure.http.presentation.getinvoice.InvoicePresenter;
import com.exo.ecommerce.usecases.additemtocart.AddItemToCart;
import com.exo.ecommerce.usecases.additemtocart.UnavailableItemExeption;
import com.exo.ecommerce.usecases.additemtocart.UnknownItemException;
import com.exo.ecommerce.usecases.checkout.CheckOut;
import com.exo.ecommerce.usecases.checkout.NothingToCheckOutException;
import com.exo.ecommerce.usecases.getallinvoices.GetAllInvoices;
import com.exo.ecommerce.usecases.getallitems.GetAllItems;
import com.exo.ecommerce.usecases.getcurrentcart.GetCurrentCart;
import com.exo.ecommerce.usecases.getcurrentcart.NoCurrentCartException;
import com.exo.ecommerce.usecases.getinvoice.GetInvoice;
import com.exo.ecommerce.usecases.getinvoice.InvoiceNotFoundException;
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
    AddItemToCartPresenter addItemToCartPresenter;

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
    public ResponseEntity addItemToCart(@RequestParam("id") Long itemId) throws UnavailableItemExeption, UnknownItemException {
        return addItemToCartPresenter.present(addItemToCart.handle(itemId), itemId);
    }

    @RequestMapping(path = "/check-out", produces = "application/json; charset=UTF-8")
    public ResponseEntity checkOutCart() throws NothingToCheckOutException {

        return checkOutPresenter.present(checkOut.handle());
    }

    @RequestMapping(path = "/cart", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public CartResponse currentCart() throws NoCurrentCartException {
        return currentCartPresenter.present(getCurrentCart.handle());
    }

    @RequestMapping(path = "/invoices", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public ArrayList<InvoiceResponse> allInvoices() {
        return invoicesPresenter.present(getAllInvoices.handle());
    }

    @RequestMapping(path = "/invoice", produces = "application/json; charset=UTF-8")
    public ResponseEntity invoice(@RequestParam("id") Long invoiceId) throws InvoiceNotFoundException {
        return invoicePresenter.present(getInvoice.handle(invoiceId));
    }
}
