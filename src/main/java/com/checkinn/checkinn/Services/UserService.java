package com.checkinn.checkinn.Services;

import com.checkinn.checkinn.Entities.User;
import com.checkinn.checkinn.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(int userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
