package com.rponce.Ticketify.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.SaveCategoryDTO;
import com.rponce.Ticketify.models.entities.Category;
import com.rponce.Ticketify.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/")
	public ResponseEntity<?> findAll(){
		List<Category> categories = categoryService.findAllCateogries();
		return new ResponseEntity<>(categories, HttpStatus.OK);
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveCategory(@ModelAttribute @Valid SaveCategoryDTO info) {
		Category category = categoryService.findByCategory(info.getCategory());
		
		if(!(category== null)) {
			return new ResponseEntity<>("Cateogry already exist", HttpStatus.BAD_REQUEST);
		}
		try {
			categoryService.saveCategory(info);
			return new ResponseEntity<>("Category created", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOneCategory(@PathVariable(name = "id") String id){
		Category category = categoryService.findOneById(id);
		
		if(category== null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable(name = "id") String id){
		Category category = categoryService.findOneById(id);
		
		if(category== null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			categoryService.deleteCategory(id);
			return new ResponseEntity<>("Category deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/category/{categoryName}")
	public ResponseEntity<?> findByCategory(@PathVariable(name = "categoryName") String categoryName){
		Category category = categoryService.findByCategory(categoryName);
		
		if(category== null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	
}
