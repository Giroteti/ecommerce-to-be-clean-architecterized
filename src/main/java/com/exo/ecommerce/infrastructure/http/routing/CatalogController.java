package com.exo.ecommerce.infrastructure.http.routing;

import com.exo.ecommerce.infrastructure.http.presentation.getallitems.ItemResponse;
import com.exo.ecommerce.infrastructure.http.presentation.getallitems.ItemsPresenter;
import com.exo.ecommerce.usecases.getallitems.GetAllItems;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class CatalogController {

    private final GetAllItems getAllItems;
    private final ItemsPresenter itemsPresenter;

    public CatalogController(GetAllItems getAllItems, ItemsPresenter itemsPresenter) {
        this.getAllItems = getAllItems;
        this.itemsPresenter = itemsPresenter;
    }

    @GetMapping(path = "/items", produces = APPLICATION_JSON_VALUE)
    public List<ItemResponse> allItems() {
        return itemsPresenter.present(getAllItems.handle());
    }
}
