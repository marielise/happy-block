package com.happy.block.service;

import com.happy.block.config.WalletServiceConfig;
import com.happy.block.entities.HappyWallet;
import com.happy.block.entities.User;
import com.happy.block.repositories.HappyWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.web3j.crypto.Credentials;

import java.io.File;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WalletServiceTest {

  @Mock
  private WalletServiceConfig walletServiceConfig;

  @Mock
  private HappyWalletRepository happyWalletRepository;

  @Mock
  private EncryptionService encryptionService;

  @Mock
  private UserService userService;

  @InjectMocks
  private WalletService walletService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateWalletForUser() throws Exception {
    // Given
    User user = new User();
    user.setUsername("testUser");

    File mockDirectory = new File("test-wallets");
    when(walletServiceConfig.getDirectory()).thenReturn(mockDirectory.getAbsolutePath());
    when(encryptionService.encrypt(anyString())).thenReturn("encryptedPrivateKey");

    HappyWallet mockWallet = HappyWallet.builder()
        .user(user)
        .address("0x123456789abcdef")
        .privateEncryptedKey("encryptedPrivateKey")
        .encryptedPassword("encryptedPassword")
        .jsonFilePath("test-wallets/wallet.json")
        .build();

    when(happyWalletRepository.save(any(HappyWallet.class))).thenReturn(mockWallet);

   // then
    HappyWallet createdWallet = walletService.createWalletForUser(user);

    assertNotNull(createdWallet);
    assertEquals("0x123456789abcdef", createdWallet.getAddress());
    verify(happyWalletRepository, times(1)).save(any(HappyWallet.class));
  }

  @Test
  void testGetWalletForUser() {
    // Given
    User user = new User();
    user.setUsername("testUser");
    HappyWallet mockWallet = new HappyWallet();
    mockWallet.setUser(user);
    mockWallet.setAddress("0xabcdef");

    when(happyWalletRepository.findByUser(user)).thenReturn(Optional.of(mockWallet));

    // then
    Optional<HappyWallet> result = walletService.getWalletForUser(user);

    assertTrue(result.isPresent());
    assertEquals("0xabcdef", result.get().getAddress());
    verify(happyWalletRepository, times(1)).findByUser(user);
  }

  @Test
  void testGetCredentialsForUser() throws Exception {
    // Arrange
    User user = new User();
    user.setUsername("testUser");
    String privateKey = "encryptedPrivateKey";

    HappyWallet mockWallet = HappyWallet.builder()
        .user(user)
        .privateEncryptedKey("encryptedPrivateKey")
        .build();

    //gives a big INT back
    String validDecryptedPrivateKey = "12345678";

    when(userService.getUser("testUser")).thenReturn(user);
    when(happyWalletRepository.findByUser(user)).thenReturn(Optional.of(mockWallet));
    when(encryptionService.decrypt("encryptedPrivateKey")).thenReturn(validDecryptedPrivateKey);

    Credentials credentials = walletService.getCredentialsForUser("testUser");

    assertNotNull(credentials);

    verify(encryptionService, times(1)).decrypt("encryptedPrivateKey");
  }

  @Test
  void testGetCredentialsForUserWalletNotFound() {
    // Given
    User user = new User();
    user.setUsername("testUser");

    when(userService.getUser("testUser")).thenReturn(user);
    when(happyWalletRepository.findByUser(user)).thenReturn(Optional.empty());

    // then 
    assertThrows(RuntimeException.class, () -> walletService.getCredentialsForUser("testUser"));
  }
}
