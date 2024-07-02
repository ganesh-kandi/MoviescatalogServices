package com.alien.MoviescatalogServices.controller;

import com.alien.MoviescatalogServices.models.CatalogItems;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MoviescatalogResources {

    @RequestMapping("/{userId}")
    public List<CatalogItems> getCatalog(String userId){
        return Collections.singletonList(
                new CatalogItems("kalki","Test",5)
        );
    }
}
