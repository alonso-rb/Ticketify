package com.rponce.Ticketify.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.User;

public interface OrderService {
	public void SaveOrder(Float total, User user, Date purchaseDate) throws Exception;
	public void DeleteOrder(Order order) throws Exception;
	List<Order> getAllOrders();
	Order findOrderById(UUID id);
	Order findCurrentOrder(Float total, User user, Date purchaseDate);
	Page<Order> findAll(int page, int size);
}
