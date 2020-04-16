package com.ebookmarket.service;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.PasswordRestToken;

public interface UserService {
    PasswordRestToken getPasswordRestToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);
}
