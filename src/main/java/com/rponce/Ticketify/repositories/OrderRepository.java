package com.rponce.Ticketify.repositories;

import java.util.Date;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.rponce.Ticketify.models.entities.Order;
import com.rponce.Ticketify.models.entities.User;

public interface OrderRepository extends JpaRepository<Order, UUID>{
	Order findOneByUuid(UUID uuid);
	Order findOneByUser(User user);
	Order findFirstOrderByUserAndPurchaseDateAndTotal(User user, Date purchaseDate, Float total);
	Page<Order> findAll(Pageable pageable);
}
