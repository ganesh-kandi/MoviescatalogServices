package com.alien.MoviescatalogServices.models;

import java.util.List;

public class UserCatalogs {
    private List<CatalogItems> catalogItems;

    public UserCatalogs() {
    }

    public List<CatalogItems> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(List<CatalogItems> catalogItems) {
        this.catalogItems = catalogItems;
    }
}
