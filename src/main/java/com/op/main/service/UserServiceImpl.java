package com.op.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.op.main.model.User;
import com.op.main.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Optional findUserByResetToken(String resetToken) {
		return userRepository.findByResetToken(resetToken);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
		
	}
}
