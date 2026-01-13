package com.bway.springproject.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.springproject.model.User;
import com.bway.springproject.repository.UserRepository;
import com.bway.springproject.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public void SignUp(User user) {
	
		userRepo.save(user);
		
	}

	@Override
	public User login(String un, String psw) {
		
		return userRepo.findByUsernameAndPassword(un, psw);
	}

	@Override
	public User checkUsername(String un) {
		
		return userRepo.findByUsername(un);
	}

}
