package com.fausto.users.users.entities;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;

@SpringBootTest
@AutoConfigureMockMvc
public class UserDataTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    
    @Test
    public void testValidName() {
        UserData user = new UserData();
        user.setName("Fausto");
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void testInvalidName() {
        UserData user = new UserData();
        user.setName("a");
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(!violations.isEmpty());
    }
    
    @Test
    public void testValidLastName() {
        UserData user = new UserData();
        user.setLastName("Rodriguez");
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void testInvalidLastName() {
        UserData user = new UserData();
        user.setLastName("b");
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(!violations.isEmpty());
    }
    
    @Test
    public void testValidBirthdate() {
        UserData user = new UserData();
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
    
    @Test
    public void testInvalidBirthdate() {
        UserData user = new UserData();
        user.setBirthDate(null);
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(!violations.isEmpty());
    }

    @Test
    public void testValidAddress() {
        UserData user = new UserData();
        user.setAddress("Vecindad del 8");
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidAddress() {
        UserData user = new UserData();
        user.setAddress("a");
        Set<ConstraintViolation<UserData>> violations = validator.validate(user);
        assertTrue(!violations.isEmpty());
    }
}
