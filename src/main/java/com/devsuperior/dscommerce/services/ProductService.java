package com.devsuperior.dscommerce.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product product = productRepository.findById(id).get();
		return new ProductDTO(product);
	}
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {
		return productRepository.findAll(pageable).map(prod -> new ProductDTO(prod));
	}
	
	@Transactional
	public ProductDTO create(ProductDTO productDTO) {
		Product product = productDTO.converteToEntity();
		productRepository.save(product);
		return new ProductDTO(product);
	}
	
	@Transactional
	public ProductDTO update(Long id, ProductDTO productDTO) {
		Product productEntity = productRepository.findById(id).get();
		productDTO.converteToEntityUpdate(productEntity);
		productRepository.save(productEntity);
		
		return new ProductDTO(productEntity);
	}
	
	@Transactional
	public void delete(Long id) {
		productRepository.deleteById(id);
	}
}
