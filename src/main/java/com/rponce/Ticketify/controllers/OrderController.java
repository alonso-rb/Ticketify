package com.rponce.Ticketify.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rponce.Ticketify.models.dtos.PageDTO;
import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.services.OrderService;

@RestController
@RequestMapping("/order")
@CrossOrigin("*")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/all")
	private ResponseEntity<?> getAllOrders (
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size){
		
		Page<Order> orderList = orderService.findAll(page, size);
		
		PageDTO<Order> response = new PageDTO<>(
				orderList.getContent(),
				orderList.getNumber(),
				orderList.getSize(),
				orderList.getTotalElements(),
				orderList.getTotalPages()
				);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/{userid}")
	private ResponseEntity<?> getOrderById(@PathVariable(name = "id") String id){
		
		UUID uuid = UUID.fromString(id);
		
		Order order = orderService.findOrderById(uuid);
		
		if(order == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}

}
