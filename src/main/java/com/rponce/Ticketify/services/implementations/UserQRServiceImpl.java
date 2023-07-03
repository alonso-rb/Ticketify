package com.rponce.Ticketify.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.SaveUserQRDTO;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.models.entities.UserQR;
import com.rponce.Ticketify.repositories.UserQRRepository;
import com.rponce.Ticketify.services.UserQRService;

@Service
public class UserQRServiceImpl implements UserQRService{

	@Autowired
	private UserQRRepository userQRRepository;
	
	@Override
	public void SaveUserQR(SaveUserQRDTO info, User userId) throws Exception {
		UserQR userQR = new UserQR();
		
		userQR.setCreationDate(info.getCreationDate());
		userQR.setQr(info.getQr());
		userQR.setStatus(true);
		userQR.setUserID(userId);
		
		userQRRepository.save(userQR);
	}

	@Override
	public List<UserQR> GetUserQRByUserId(User user) {

		return userQRRepository.findAllByUserID(user);
	}

	@Override
	public UserQR GetUserQRByQR(String qr) {
		
		return userQRRepository.findOneByQr(qr);
	}

}
