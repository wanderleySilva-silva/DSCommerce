package com.devsuperior.dscommerce.dto;

import com.devsuperior.dscommerce.entities.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;
	
	@NotBlank(message = "Campo requerido")
	@Size(min = 3, max = 80, message = "Nome precisa ter de 3 a 80 caracteres")
	private String name;
	
	@NotBlank(message = "Campo requerido")
	@Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres")
	private String description;
	
	@Positive(message = "O preço deve ser positivo")
	private Double price;
	private String imgUrl;
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
	}
	
	public Product converteToEntity() {
		Product product = new Product();
		copyDtoToEntity(product);
		return product;
	}

	public Product converteToEntityUpdate(Product product) {
		copyDtoToEntity(product);
		return product;
	}
	
	private void copyDtoToEntity(Product product) {
		product.setName(name);
		product.setDescription(description);
		product.setImgUrl(imgUrl);
		product.setPrice(price);
	}
		
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

}
