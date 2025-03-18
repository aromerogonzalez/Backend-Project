package com.fausto.users.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fausto.users.users.entities.UserData;

public interface UserRepository extends JpaRepository<UserData, Integer> {

}
