package com.happy.block.controllers;


import com.happy.block.domain.users.NewUserDao;
import com.happy.block.domain.users.UserRegistrationDao;
import com.happy.block.entities.User;
import com.happy.block.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path={"/api/users"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Manage users", description = "Add new or register users, see there addresses")
public class UserController {

  private final UserService userService;

  @PostMapping
  @Operation(summary = "Create a brand new user", description = "Creates a new user with a new address")
  public ResponseEntity<User> createUser(@Valid @RequestBody NewUserDao userDao){
    User user = userService.createUser(userDao);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/register")
  @Operation(summary = "Register a user", description = "Register a user with an known address")
  public ResponseEntity<User> registerUser(@Valid @RequestBody UserRegistrationDao userDao)
      throws BadRequestException {
    User user = userService.registerUser(userDao);
    return ResponseEntity.ok(user);
  }


}
