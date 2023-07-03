package com.rponce.Ticketify.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.Category;

public interface CategoryRepository extends ListCrudRepository<Category, String>{
	Category findByCategory(String category);
}
