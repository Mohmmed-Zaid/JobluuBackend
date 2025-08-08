package com.Cubix.Jobluu.repositories;

import com.Cubix.Jobluu.entities.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface ProfileRepository extends MongoRepository<Profile,Long> {
}
