package com.op.main.service;
import java.util.Collection;
import java.util.Optional;

import com.op.main.model.User;
public interface UserService {
	public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserByResetToken(String resetToken);
  
    public void save(User user);
	public void saveUser(User user);
}
