package com.ebookmarket.service.impl;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.PasswordRestToken;
import com.ebookmarket.domain.security.UserRole;
import com.ebookmarket.repository.PasswordResetTokenRepository;
import com.ebookmarket.repository.RoleRepository;
import com.ebookmarket.repository.UserRepository;
import com.ebookmarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null) {
            throw new Exception("user already exists. Nothing will be doing");
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }

            user.getUserRoles().addAll(userRoles);

            localUser = userRepository.save(user);
        }

        return localUser;
    }
}
