package com.sayeed.saudiMartAuth.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayeed.saudiMartAuth.Model.ResponseWrapper;
import com.sayeed.saudiMartAuth.Model.Users;
import com.sayeed.saudiMartAuth.Service.UserService;
import com.sayeed.saudiMartAuth.Utils.UserException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<List<Users>>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", users));
    }

    @PostMapping("/{email}/{role}")
    public ResponseEntity<ResponseWrapper<Boolean>> updateRole(String email, String role) throws UserException {
        userService.updateRole(email, role);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Users>> getUserById(@PathVariable String id) throws UserException {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", user));
    }

    @PostMapping("/{email}/{activated}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> toggleUserActivation(@PathVariable String email,
            @PathVariable boolean activated)
            throws UserException {
        userService.toggleUserActivation(email, activated);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    @PostMapping("/{email}/{locked}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> lockOrUnlockUser(@PathVariable String email,
            @PathVariable boolean locked)
            throws UserException {
        userService.lockOrUnlockUser(email, locked);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    @PostMapping("/{email}/{verified}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> setUserVerificationStatus(@PathVariable String email,
            @PathVariable boolean verified)
            throws UserException {
        userService.setUserVerificationStatus(email, verified);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseWrapper<Users>> updateUserSelf(Users user) throws UserException {
        Users updatedUser = userService.updateUserSelf(user);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", updatedUser));
    }

    @GetMapping("/is-email-available/{email}")
    public ResponseEntity<ResponseWrapper<Boolean>> isEmailAvailable(@PathVariable String email) throws UserException {
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", available));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseWrapper<Users>> getUserByEmail(@PathVariable String email) throws UserException {
        Users user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable String id) throws UserException {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "User deleted successfully", null));
    }
}
