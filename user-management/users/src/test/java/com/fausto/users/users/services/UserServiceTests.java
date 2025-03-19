package com.fausto.users.users.services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.fausto.users.users.entities.UserData;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTests {
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCreateUser() {
        UsersService usersService = new UsersService();
        UserData user = new UserData();
        user.setName("Fausto");
        user.setLastName("Rodriguez");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("Calle 13");
        usersService.setUserDetails(user);
        Set<ConstraintViolation<UsersService>> violations = validator.validate(usersService);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUpdateUser() {
        UsersService usersService = new UsersService();
        UserData user = new UserData();
        user.setId(1);
        user.setName("Fausto");
        user.setLastName("Rodriguez");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("Calle 13");
        usersService.updateUser(user);
        Set<ConstraintViolation<UsersService>> violations = validator.validate(usersService);
        assertTrue(violations.isEmpty());
    }

    @Test
    private void testGetUserDetails() {
        UsersService usersService = new UsersService();
        usersService.getUserDetails(1);
        Set<ConstraintViolation<UsersService>> violations = validator.validate(usersService);
        assertTrue(violations.isEmpty());
    }
}