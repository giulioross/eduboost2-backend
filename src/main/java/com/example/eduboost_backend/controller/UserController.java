package com.example.eduboost_backend.controller;

import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.service.CloudinaryService;
import com.example.eduboost_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody User userDetails) {
        User currentUser = userService.getCurrentUser();

        // Update only allowed fields
        currentUser.setFirstName(userDetails.getFirstName());
        currentUser.setLastName(userDetails.getLastName());
        currentUser.setUserType(userDetails.getUserType());

        User updatedUser = userService.updateUser(currentUser);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/me/profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file) throws IOException {
        User currentUser = userService.getCurrentUser();

        String imageUrl = cloudinaryService.uploadFile(file);
        currentUser.setProfilePictureUrl(imageUrl);
        userService.updateUser(currentUser);

        return ResponseEntity.ok(new MessageResponse("Profile picture updated successfully!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }
}