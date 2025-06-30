package com.devsuperior.dscommerce.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findById(id));
	}

	@GetMapping
	public ResponseEntity<Page<ProductDTO>> findAll(@RequestParam(name = "name", defaultValue = "") String name, Pageable pageable) {
		return ResponseEntity.status(HttpStatus.OK).body(productService.findAll(name, pageable));
	}

	@PostMapping
	public ResponseEntity<ProductDTO> create(@RequestBody @Valid ProductDTO productDTO) {
		ProductDTO newProductDTO = productService.create(productDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newProductDTO.getId())
				.toUri();

		return ResponseEntity.created(uri).body(newProductDTO);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
		ProductDTO newProductDTO = productService.update(id, productDTO);

		return ResponseEntity.ok(newProductDTO);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();

	}
}
