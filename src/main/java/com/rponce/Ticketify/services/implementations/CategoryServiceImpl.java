package com.rponce.Ticketify.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveCategoryDTO;
import com.rponce.Ticketify.models.entities.Category;
import com.rponce.Ticketify.repositories.CategoryRepository;
import com.rponce.Ticketify.services.CategoryService;

import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void saveCategory(SaveCategoryDTO info) throws Exception {
		
		Category category = new Category(
				info.getId(),
				info.getCategory()
				);
		
		categoryRepository.save(category);
	}

	@Override
	public Category findOneById(String id) {
		try {
			return categoryRepository.findById(id)
					.orElse(null);
		} catch(Exception error) {
			return null;
		}
	}

	@Override
	public List<Category> findAllCateogries() {
		return categoryRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteCategory(String id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public Category findByCategory(String cateogry) {
		try {
			return categoryRepository.findByCategory(cateogry);
			} catch (Exception error) {
			return null;
		}
	}

}
