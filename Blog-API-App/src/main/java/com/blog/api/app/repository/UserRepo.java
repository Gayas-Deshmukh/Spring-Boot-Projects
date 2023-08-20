package com.blog.api.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.api.app.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>
{

}
