package com.Cubix.Jobluu.repositories;

import com.Cubix.Jobluu.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,Long> {

    public Optional<User> findByEmail(String email);

}
