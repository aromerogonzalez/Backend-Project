package com.fausto.users.users.controllers;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fausto.users.users.entities.UserData;
import com.fausto.users.users.services.UsersService;
import com.fausto.users.users.services.Kafka.KafkaProducerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    UsersService userService;

    @Autowired
    KafkaProducerService kafkaProducer;
    
    @PostMapping("/register")
    public UserData postUser(@RequestBody UserData user) {
        return userService.setUserDetails(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserData user) {
        Key secureKey = Keys.hmacShaKeyFor("BcfWPFzRvL04zNqZdEfj4CP01/WfEecQj7N03s/byqs=".getBytes());
        return Jwts.builder().setSubject(user.getId().toString()).signWith(secureKey,SignatureAlgorithm.HS256).compact();
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable Integer userId) {
        return userService.getUserDetails(userId);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody UserData user) {
        user.setId(userId);
        return userService.updateUser(user);
    }
    
}
