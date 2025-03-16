package com.happy.block.controllers;

import com.happy.block.domain.NewUserDao;
import com.happy.block.domain.UserRegistrationDao;
import com.happy.block.entities.User;
import com.happy.block.service.UserService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateUser() {
    // Given
    NewUserDao newUserDao = new NewUserDao("testUser");
    User mockUser = new User();
    mockUser.setUsername("testUser");

    //when
    when(userService.createUser(newUserDao)).thenReturn(mockUser);
    ResponseEntity<User> response = userController.createUser(newUserDao);

    // Then
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("testUser", response.getBody().getUsername());

    verify(userService, times(1)).createUser(newUserDao);
  }

  @Test
  void testRegisterUser() throws BadRequestException {
    // Given
    UserRegistrationDao userRegistrationDao = new UserRegistrationDao("testUser", "0x123456789abcdef");
    User mockUser = new User();
    mockUser.setUsername("testUser");
    mockUser.setEthAddress("0x123456789abcdef");

    when(userService.registerUser(userRegistrationDao)).thenReturn(mockUser);
    ResponseEntity<User> response = userController.registerUser(userRegistrationDao);

    // then
    assertEquals(200, response.getStatusCode().value());
    assertNotNull(response.getBody());
    assertEquals("testUser", response.getBody().getUsername());
    assertEquals("0x123456789abcdef", response.getBody().getEthAddress());

    verify(userService, times(1)).registerUser(userRegistrationDao);
  }

  @Test
  void testRegisterUserWithInvalidEthAddress() throws BadRequestException {
    // Given
    UserRegistrationDao userRegistrationDao = new UserRegistrationDao("testUser", "invalidAddress");
    when(userService.registerUser(userRegistrationDao)).thenThrow(new BadRequestException("Invalid Ethereum Address"));

    //Then
    Exception exception = assertThrows(BadRequestException.class, () -> userController.registerUser(userRegistrationDao));
    assertEquals("Invalid Ethereum Address", exception.getMessage());

    verify(userService, times(1)).registerUser(userRegistrationDao);
  }
}
