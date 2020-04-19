package com.ebookmarket;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.Role;
import com.ebookmarket.domain.security.UserRole;
import com.ebookmarket.service.UserService;
import com.ebookmarket.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class EbookMarketApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(EbookMarketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setFirstName("amin");
        user1.setLastName("ghazi");
        user1.setUsername("amin");
        user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
        user1.setEmail("aminghazi@outlook.com");
        Set<UserRole> userRoles = new HashSet<>();
        Role role1 = new Role();
        role1.setRoleId(1);
        role1.setName(ROLE_USER);
        userRoles.add(new UserRole(user1, role1));

        userService.createUser(user1, userRoles);
    }

}
