package com.rponce.Ticketify.services;

import java.util.List;

import com.rponce.Ticketify.models.dtos.SaveCategoryDTO;
import com.rponce.Ticketify.models.entities.Category;

public interface CategoryService {
	void saveCategory(SaveCategoryDTO info) throws Exception;
	Category findOneById(String id);
	List<Category> findAllCateogries();
	void deleteCategory(String id) throws Exception;
	Category findByCategory(String cateogry);
}
