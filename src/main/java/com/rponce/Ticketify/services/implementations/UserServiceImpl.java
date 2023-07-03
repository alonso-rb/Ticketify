package com.rponce.Ticketify.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rponce.Ticketify.models.dtos.AuthUserDTO;
import com.rponce.Ticketify.models.dtos.RecuperatePasswordDTO;
import com.rponce.Ticketify.models.dtos.SaveUserDTO;
import com.rponce.Ticketify.models.dtos.UpdatePasswordDTO;
import com.rponce.Ticketify.models.entities.Token;
import com.rponce.Ticketify.models.entities.User;
import com.rponce.Ticketify.repositories.TokenRepository;
import com.rponce.Ticketify.repositories.UserRepository;
import com.rponce.Ticketify.services.UserService;
import com.rponce.Ticketify.utils.JWTTools;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTTools jwtTools;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void SaveUser(SaveUserDTO info) throws Exception {
		
		User user = new User();
		
		user.setEmail(info.getEmail());
		user.setFirstname(info.getFirstName());
		user.setLastname(info.getLastName());
		user.setPassword(passwordEncoder.encode(info.getPassword()));
		user.setActive(true);
		
		userRepository.save(user);
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public User AuthUser(AuthUserDTO info) {
		User userToAuth = userRepository.findOneByEmail(info.getEmail());
		Boolean match = passwordEncoder.matches(info.getPassword(), userToAuth.getPassword());
		
		if(match == true){
			return userToAuth;
		}
		
		return null;
		
	}

	@Override
	public void DeactivateUser(User user) throws Exception {
		
		user.setActive(false);
		userRepository.save(user);
		
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<User> GetAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public User FindOneUserById(String id) {
		
		UUID uuid = UUID.fromString(id);
		return userRepository.findOneByUuid(uuid);

	}

	@Override
	public User FindOneUserByEmail(String email) {
		
		return userRepository.findOneByEmail(email);
				
	}

	@Override
	public void RecuperatePassword(RecuperatePasswordDTO data, User user) {
		
		user.setPassword(passwordEncoder.encode(data.getNewPassword()));
		userRepository.save(user);
		
	}

	@Override
	public void UpdatePassword(UpdatePasswordDTO info, User user) {
		
		user.setPassword(passwordEncoder.encode(info.getNewPassword()));
		userRepository.save(user);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Token registerToken(User user) throws Exception {
		cleanTokens(user);
		
		String tokenString = jwtTools.generateToken(user);
		Token token = new Token(tokenString, user);
		
		tokenRepository.save(token);
		
		return token;
	}

	@Override
	public Boolean isTokenValid(User user, String token) {
		try {
			cleanTokens(user);
			List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
			
			tokens.stream()
				.filter(tk -> tk.getContent().equals(token))
				.findAny()
				.orElseThrow(() -> new Exception());
			
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	public void cleanTokens(User user) throws Exception {
		List<Token> tokens = tokenRepository.findByUserAndActive(user, true);
		
		tokens.forEach(token -> {
			if(!jwtTools.verifyToken(token.getContent())) {
				token.setActive(false);
				tokenRepository.save(token);
			}
		});
		
	}

	@Override
	public User findUserAuthenticated() {
		String username = SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getName();
			
			return userRepository.findOneByEmail(username);
	}

}
