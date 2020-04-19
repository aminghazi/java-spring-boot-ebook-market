package com.ebookmarket.controller;

import com.ebookmarket.domain.User;
import com.ebookmarket.domain.security.PasswordRestToken;
import com.ebookmarket.domain.security.Role;
import com.ebookmarket.domain.security.UserRole;
import com.ebookmarket.service.UserService;
import com.ebookmarket.service.impl.UserSecurityService;
import com.ebookmarket.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Controller
public class HomeController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailConstractor mailConstractor;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/account")
    public String account() {
        return "account";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(
        HttpServletRequest request,
        @ModelAttribute("email") String userEmail,
        @ModelAttribute("username") String username,
        Model model
        ) throws Exception {
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);

            return "account";
        }

        if (userService.findByEmail( userEmail) != null) {
            model.addAttribute("email", true);

            return "account";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));
        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();

        SimpleMailMessage email = mailConstractor.constractRestTokenEmail(appUrl, request.getLocale(), token, user, password);

        javaMailSender.send(email);

        model.addAttribute("emailSent", "true");

        return "account";

    }

    @RequestMapping("/newUser")
    public String newUser(
            Locale locale,
            @RequestParam("token") String token,
            Model model) {
        PasswordRestToken passwordRestToken = userService.getPasswordRestToken(token);

        if (passwordRestToken == null) {
            String message = "Invalid Token.";
            model.addAttribute("message", message);
            return "redirect:/badRequest";
        }

        User user = passwordRestToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("classActiveEdit", true);
        return "myProfile";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "account";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(Model model) {

        model.addAttribute("classActiveForgetPassword", true);
        return "account";
    }
}
