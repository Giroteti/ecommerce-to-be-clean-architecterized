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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ECommerceController {

    private final AddItemToCart addItemToCart;
    private final AddItemToCartPresenter addItemToCartPresenter;

    private final CheckOut checkOut;
    private final CheckOutPresenter checkOutPresenter;

    private final GetCurrentCart getCurrentCart;
    private final CurrentCartPresenter currentCartPresenter;

    private final GetAllInvoices getAllInvoices;
    private final InvoicesPresenter invoicesPresenter;

    private final GetInvoice getInvoice;
    private final InvoicePresenter invoicePresenter;

    private final GetAllItems getAllItems;
    private final ItemsPresenter itemsPresenter;

    public ECommerceController(GetAllInvoices getAllInvoices, AddItemToCart addItemToCart, AddItemToCartPresenter addItemToCartPresenter, CheckOut checkOut, CheckOutPresenter checkOutPresenter, GetCurrentCart getCurrentCart, CurrentCartPresenter currentCartPresenter, InvoicesPresenter invoicesPresenter, GetInvoice getInvoice, InvoicePresenter invoicePresenter, GetAllItems getAllItems, ItemsPresenter itemsPresenter) {
        this.getAllInvoices = getAllInvoices;
        this.addItemToCart = addItemToCart;
        this.addItemToCartPresenter = addItemToCartPresenter;
        this.checkOut = checkOut;
        this.checkOutPresenter = checkOutPresenter;
        this.getCurrentCart = getCurrentCart;
        this.currentCartPresenter = currentCartPresenter;
        this.invoicesPresenter = invoicesPresenter;
        this.getInvoice = getInvoice;
        this.invoicePresenter = invoicePresenter;
        this.getAllItems = getAllItems;
        this.itemsPresenter = itemsPresenter;
    }

    @GetMapping(path = "/items", produces = APPLICATION_JSON_VALUE)
    public List<ItemResponse> allItems() {
        return itemsPresenter.present(getAllItems.handle());
    }

    @RequestMapping(method = {GET, POST}, path = "/add-item", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity addItemToCart(@RequestParam("id") Long itemId) throws UnavailableItemExeption, UnknownItemException {
        return addItemToCartPresenter.present(addItemToCart.handle(itemId), itemId);
    }

    @GetMapping(path = "/check-out", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity checkOutCart() throws NothingToCheckOutException {
        return checkOutPresenter.present(checkOut.handle());
    }

    @GetMapping(path = "/cart", produces = APPLICATION_JSON_VALUE)
    public CartResponse currentCart() throws NoCurrentCartException {
        return currentCartPresenter.present(getCurrentCart.handle());
    }

    @GetMapping(path = "/invoices", produces = APPLICATION_JSON_VALUE)
    public List<InvoiceResponse> allInvoices() {
        return invoicesPresenter.present(getAllInvoices.handle());
    }

    @GetMapping(path = "/invoice", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity invoice(@RequestParam("id") Long invoiceId) throws InvoiceNotFoundException {
        return invoicePresenter.present(getInvoice.handle(invoiceId));
    }
}
