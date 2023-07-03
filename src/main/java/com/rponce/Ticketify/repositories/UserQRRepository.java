package com.rponce.Ticketify.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserQR;

public interface UserQRRepository extends ListCrudRepository<UserQR, UUID>{
	List<UserQR> findAllByUserID(User user);
	UserQR findOneByQr(String qr);
}
