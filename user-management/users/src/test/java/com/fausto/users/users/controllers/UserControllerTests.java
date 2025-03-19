package com.fausto.users.users.controllers;

import static org.junit.Assert.assertTrue;

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
public class UserControllerTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testPost() {
        UserController userController = new UserController();
        UserData user = new UserData();
        user.setName("Fausto");
        user.setLastName("Rodriguez");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("Calle 13");
        userController.postUser(user);
        Set<ConstraintViolation<UserController>> violations = validator.validate(userController);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testGet() {
        UserController userController = new UserController();
        userController.getUserDetails(1);
        Set<ConstraintViolation<UserController>> violations = validator.validate(userController);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testUpdateUser() {
        UserController userController = new UserController();
        UserData user = new UserData();
        user.setId(1);
        user.setName("Fausto");
        user.setLastName("Rodriguez");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("Calle 13");
        userController.updateUser(1, user);
        Set<ConstraintViolation<UserController>> violations = validator.validate(userController);
        assertTrue(violations.isEmpty());
    }

    @Test void testLogin() {
        UserController userController = new UserController();
        UserData user = new UserData();
        user.setId(1);
        user.setName("Fausto");
        user.setLastName("Rodriguez");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setAddress("Calle 13");
        userController.login(user);
        Set<ConstraintViolation<UserController>> violations = validator.validate(userController);
        assertTrue(violations.isEmpty());
    }
}
