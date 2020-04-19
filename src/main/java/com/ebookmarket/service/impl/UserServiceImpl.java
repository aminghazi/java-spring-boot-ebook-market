package com.ebookmarket.service.impl;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.PasswordRestToken;
import com.ebookmarket.repository.PasswordResetTokenRepository;
import com.ebookmarket.repository.UserRepository;
import com.ebookmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public PasswordRestToken getPasswordRestToken(final String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordRestToken passwordRestToken = new PasswordRestToken(token, user);
        passwordResetTokenRepository.save(passwordRestToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
