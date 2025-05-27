package com.saudiMart.Auth.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.saudiMart.Auth.Model.ResponseWrapper;
import com.saudiMart.Auth.Model.Users;
import com.saudiMart.Auth.Service.UserService;
import com.saudiMart.Auth.Utils.UserException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets all users in the system.
     * Requires ROLE_ADMIN.
     */
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<List<Users>>> getAllUsers() {
        List<Users> users = userService.getAllUsers();
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", users));
    }

    /**
     * Gets a user by ID.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ResponseWrapper<Users>> getUserById(@PathVariable("id") String id) throws UserException {
        Users user = userService.getUserById(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", user));
    }

    /**
     * Gets a user by their email address.
     */
    @GetMapping("/email/{email:.+}")
    public ResponseEntity<ResponseWrapper<Users>> getUserByEmail(@PathVariable("email") String email)
            throws UserException {
        Users user = userService.getUserByEmail(email);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", user));
    }

    /**
     * Checks if the given email is available.
     */
    @GetMapping("/is-email-available/{email:.+}")
    public ResponseEntity<ResponseWrapper<Boolean>> isEmailAvailable(@PathVariable("email") String email)
            throws UserException {
        boolean available = userService.isEmailAvailable(email);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", available));
    }

    /**
     * Used by Admin to change a user's role.
     * Requires ROLE_ADMIN.
     */
    @PostMapping("/set-role/{email:.+}/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> updateRole(@PathVariable("email") String email,
            @PathVariable("role") String role)
            throws UserException {
        userService.updateRole(email, role);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    /**
     * Toggles a user's activation status.
     * Requires ROLE_ADMIN.
     */
    @PostMapping("/activate/{email:.+}/{activated}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> toggleUserActivation(@PathVariable("email") String email,
            @PathVariable("activated") boolean activated) throws UserException {
        userService.toggleUserActivation(email, activated);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    /**
     * Locks or unlocks a user's account.
     * Requires ROLE_ADMIN.
     */
    @PostMapping("/lock/{email:.+}/{locked}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> lockOrUnlockUser(@PathVariable("email") String email,
            @PathVariable("locked") boolean locked) throws UserException {
        userService.lockOrUnlockUser(email, locked);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    /**
     * Verifies or unverifies a user.
     * Requires ROLE_ADMIN.
     */
    @PostMapping("/verify/{email:.+}/{verified}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Boolean>> setUserVerificationStatus(@PathVariable("email") String email,
            @PathVariable("verified") boolean verified) throws UserException {
        userService.setUserVerificationStatus(email, verified);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", true));
    }

    /**
     * Updates the profile details of the currently logged-in user.
     */
    @PostMapping("/update")
    public ResponseEntity<ResponseWrapper<Users>> updateUserSelf(@RequestBody Users user) throws UserException {
        Users updatedUser = userService.updateUserSelf(user);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "Success", updatedUser));
    }

    /**
     * Deletes a user by ID.
     * Requires ROLE_ADMIN.
     */
    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable("id") String id) throws UserException {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseWrapper<>(200, "User deleted successfully", null));
    }
}
