package com.akihito.spring_security.controllers;

import com.akihito.spring_security.dtos.ProductsRecordDTO;
import com.akihito.spring_security.models.ProductModel;
import com.akihito.spring_security.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<ProductModel> createProduct(
            @RequestBody @Valid ProductsRecordDTO productsRecordDTO
    ) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productsRecordDTO, productModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productRepository.save(productModel));
    }
}
