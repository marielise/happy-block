package com.happy.block.service;

import com.happy.block.domain.users.NewUserDao;
import com.happy.block.domain.users.UserRegistrationDao;
import com.happy.block.entities.User;
import com.happy.block.repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.happy.block.common.Utils.generateEthereumAddress;
import static com.happy.block.common.Utils.isValidEthereumAddress;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateUser() {

    NewUserDao newUserDao = new NewUserDao("testUser");
    User mockUser = User.builder()
        .username(newUserDao.getUserName())
        .ethAddress(generateEthereumAddress()) // Generate mock address
        .build();

    when(userRepository.save(any(User.class))).thenReturn(mockUser);

    User createdUser = userService.createUser(newUserDao);

    assertNotNull(createdUser);
    assertEquals("testUser", createdUser.getUsername());
    assertTrue(isValidEthereumAddress(createdUser.getEthAddress()));

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testRegisterUser() throws BadRequestException {
    //given
    UserRegistrationDao userRegistrationDao = new UserRegistrationDao("testUser", "0x2fb9177ed1d952b3cf2c6821f5db3f5cf9ddfdf9");
    User mockUser = User.builder()
        .username(userRegistrationDao.getUsername())
        .ethAddress(userRegistrationDao.getEthAddress())
        .build();

    when(userRepository.save(any(User.class))).thenReturn(mockUser);

    User registeredUser = userService.registerUser(userRegistrationDao);

    assertNotNull(registeredUser);
    assertEquals("testUser", registeredUser.getUsername());
    assertEquals("0x2fb9177ed1d952b3cf2c6821f5db3f5cf9ddfdf9", registeredUser.getEthAddress());

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  void testRegisterUserWithInvalidEthAddress() {

    UserRegistrationDao userRegistrationDao = new UserRegistrationDao("testUser", "invalidAddresssdsx");

    Exception exception = assertThrows(BadRequestException.class, () -> userService.registerUser(userRegistrationDao));
    assertEquals("Invalid Ethereum Address", exception.getMessage());

    verify(userRepository, never()).save(any(User.class));
  }

  @Test
  void testSaveUser() {

    User user = User.builder()
        .username("testUser")
        .ethAddress("0x123456789abcdef")
        .build();

    when(userRepository.save(user)).thenReturn(user);

    User savedUser = userService.saveUser(user);

    assertNotNull(savedUser);
    assertEquals("testUser", savedUser.getUsername());

    verify(userRepository, times(1)).save(user);
  }

  @Test
  void testGetUserWhenUserExists() {
    // Arrange
    User mockUser = User.builder().username("testUser").build();
    when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

    // Act
    User foundUser = userService.getUser("testUser");

    // Assert
    assertNotNull(foundUser);
    assertEquals("testUser", foundUser.getUsername());

    verify(userRepository, times(1)).findByUsername("testUser");
  }

  @Test
  void testGetUserWhenUserDoesNotExist() {
    // Arrange
    when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

    // Act
    User foundUser = userService.getUser("nonExistentUser");

    // Assert
    assertNull(foundUser);

    verify(userRepository, times(1)).findByUsername("nonExistentUser");
  }
}
