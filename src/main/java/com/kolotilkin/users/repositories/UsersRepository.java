package com.kolotilkin.users.repositories;

import com.kolotilkin.users.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, String> {

    User findByEmail(String email);
}
