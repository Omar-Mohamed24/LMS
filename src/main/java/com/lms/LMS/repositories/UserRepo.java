package com.lms.LMS.repositories;

import com.lms.LMS.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String>
{
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
