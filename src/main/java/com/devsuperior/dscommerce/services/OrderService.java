package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.dto.OrderItemDTO;
import com.devsuperior.dscommerce.entities.*;
import com.devsuperior.dscommerce.repositories.OrderItemRepository;
import com.devsuperior.dscommerce.repositories.OrderRepository;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderItemRepository orderItemRepository;

    private UserService userService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderItemRepository orderItemRepository,UserService userService){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        Order order = new Order();

        User user = userService.authenticated();
        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);
        order.setClient(user);

        for(OrderItemDTO itemDTO : orderDTO.getItems()){
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem orderItem = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItens().add(orderItem);
        }
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItens());

        return new OrderDTO(order);
    }
}
