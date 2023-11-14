package com.rponce.Ticketify.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.repositories.OrderRepository;
import com.rponce.Ticketify.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@Override
	public Order findOrderById(UUID id) {

		return orderRepository.findOneByUuid(id);
	}

	@Override
	public void SaveOrder(Float total, User user, Date purchaseDate) throws Exception {

		Order newOrder = new Order();

		newOrder.setUser(user);
		newOrder.setPurchaseDate(purchaseDate);
		newOrder.setTotal(total);

		orderRepository.save(newOrder);

	}

	@Override
	public Order findCurrentOrder(Float total, User user, Date purchaseDate) {

		return orderRepository.findFirstOrderByUserAndPurchaseDateAndTotal(user, purchaseDate, total);
	}

	@Override
	public void DeleteOrder(Order order) throws Exception {

		orderRepository.delete(order);

	}

	@Override
	public Page<Order> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return orderRepository.findAll(pageable);
	}

}
