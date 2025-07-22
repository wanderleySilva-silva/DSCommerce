package com.devsuperior.dscommerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.repositories.CategoryRepository;

@Service
public class CategoryService {

	private CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryDTO> findAll() {

		return categoryRepository.findAll().stream().map(c -> new CategoryDTO(c)).toList();
	}
}
