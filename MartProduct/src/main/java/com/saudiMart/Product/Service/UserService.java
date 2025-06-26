package com.saudiMart.Product.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saudiMart.Product.Model.Users;
import com.saudiMart.Product.Repository.UserRepository;
import com.saudiMart.Product.Utils.ProductException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Users getUserById(String userId) throws ProductException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ProductException("User not found with ID: " + userId));
    }

    @Transactional(readOnly = true)
    public Users getUserByEmail(String email) throws ProductException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ProductException("User not found with email: " + email));
    }

}
