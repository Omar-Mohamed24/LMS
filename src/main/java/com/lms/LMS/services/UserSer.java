package com.lms.LMS.services;

import com.lms.LMS.exceptions.EmailAlreadyRegisteredException;
import com.lms.LMS.exceptions.InvalidPasswordException;
import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.Role;
import com.lms.LMS.models.User;
import com.lms.LMS.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserSer
{
    private static final String PERMISSION_DENIED_MESSAGE = "Permission Denied";
    private final UserRepo repository;
    private final BCryptPasswordEncoder encoder;

    public User register(User user) throws Exception
    {
        if (repository.existsByEmail(user.getEmail()))
        {
            throw new EmailAlreadyRegisteredException("Email is already registered: " + user.getEmail());
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());
        user = repository.save(newUser);
        return user;
    }

    public User login(User user)
    {
        User foundUser = repository.findByEmail(user.getEmail()).orElseThrow(() -> new NotFound("User with the provided email does not exist."));
        if(!encoder.matches(user.getPassword(), foundUser.getPassword()))
        {
            throw new InvalidPasswordException("Invalid password. Please try again.");
        }
        return foundUser;
    }

    public User getCurrentUser()
    {
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findById(currentUserId).orElseThrow(() -> new NotFound("User not found"));
    }

    public User updateUserProfile(String userId, User updatedUser)
    {
        User currentUser = repository.findById(userId).orElseThrow(() -> new NotFound("User not found"));

        if (currentUser.getId().equals(userId) || currentUser.getRole().equals(Role.ADMIN))
        {

            if (updatedUser.getName() != null && !updatedUser.getName().isEmpty())
            {
                currentUser.setName(updatedUser.getName());
            }

            if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty())
            {
                currentUser.setEmail(updatedUser.getEmail());
            }

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty())
            {
                currentUser.setPassword(encoder.encode(updatedUser.getPassword()));
            }

            return repository.save(currentUser);
        }
        else
        {
            throw new RuntimeException(PERMISSION_DENIED_MESSAGE);
        }
    }


    public void deleteUser(String userId)
    {
        User currentUser = getCurrentUser();
        if (currentUser.getId().equals(userId) || currentUser.getRole().equals(Role.ADMIN))
        {
            repository.deleteById(userId);
        }
        else
        {
            throw new RuntimeException(PERMISSION_DENIED_MESSAGE);
        }
    }
}
