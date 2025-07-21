package com.devsuperior.dscommerce.controllers;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(path = "/orders")
public class OrderController {

	private OrderService orderService;

	public OrderController(OrderService orderService){
		this.orderService = orderService;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
	@GetMapping(path = "/{id}")
	public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
	}

	@PreAuthorize("hasRole('ROLE_CLIENT')")
	@PostMapping
	public ResponseEntity<OrderDTO> create(@RequestBody @Valid OrderDTO orderDTO) {
		OrderDTO newOrderDTO = orderService.create(orderDTO);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOrderDTO.getId())
				.toUri();

		return ResponseEntity.created(uri).body(newOrderDTO);
	}
}
