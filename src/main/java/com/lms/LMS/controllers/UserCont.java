package com.lms.LMS.controllers;

import com.lms.LMS.exceptions.InvalidPasswordException;
import com.lms.LMS.exceptions.NotFound;
import com.lms.LMS.models.User;
import com.lms.LMS.repositories.UserRepo;
import com.lms.LMS.services.JwtService;
import com.lms.LMS.services.UserSer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCont
{
    private final UserSer userser;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) throws Exception
    {
        try
        {
            User saved = userser.register(user);

            String token = jwtService.generateToken(saved);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", saved);

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user)
    {
        try
        {
            User foundUser = userser.login(user);

            String token = jwtService.generateToken(foundUser);
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", foundUser);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (NotFound | InvalidPasswordException e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
    }

    // Endpoint to fetch the current logged-in user's profile
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser()
    {
        try
        {
            User currentUser = userser.getCurrentUser();
            return new ResponseEntity<>(currentUser, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody User updatedUser)
    {
        try
        {
            User savedUser = userser.updateUserProfile(userId, updatedUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }


    // Endpoint to delete a user (only ADMIN can delete)
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId)
    {
        try
        {
            userser.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.FORBIDDEN);
        }
    }
}
