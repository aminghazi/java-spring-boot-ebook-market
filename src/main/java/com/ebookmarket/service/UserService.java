package com.ebookmarket.service;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.PasswordRestToken;
import com.ebookmarket.domain.security.UserRole;

import java.util.Set;

public interface UserService {
    PasswordRestToken getPasswordRestToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;
}
