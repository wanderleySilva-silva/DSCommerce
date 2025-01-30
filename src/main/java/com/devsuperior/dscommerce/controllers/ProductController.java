package com.devsuperior.dscommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
	}

}
