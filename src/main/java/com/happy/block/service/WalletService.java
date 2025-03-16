package com.happy.block.service;

import static com.happy.block.common.Utils.convertToHex;

import com.happy.block.config.WalletServiceConfig;
import com.happy.block.entities.HappyWallet;
import com.happy.block.entities.User;
import com.happy.block.repositories.HappyWalletRepository;
import java.io.File;
import java.util.Optional;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;

@Service
@Slf4j
@AllArgsConstructor
public class WalletService {

  private final WalletServiceConfig walletServiceConfig;
  private final HappyWalletRepository happyWalletRepository;
  private final EncryptionService encryptionService;
  private final UserService userService;

  public HappyWallet createWalletForUser(User user){
    Random random = new Random();
    String walletPassword = user.getUsername() + user.hashCode() + random.nextInt(100);

    try {

      File directory = new File(walletServiceConfig.getDirectory());
      if (!directory.exists()) {
        log.info("Create directory for wallet {}", directory.getAbsolutePath());
        boolean result = directory.mkdirs();

      }

      String walletFile = WalletUtils.generateLightNewWalletFile(walletPassword, directory);

      Credentials credentials = WalletUtils.loadCredentials(walletPassword, walletServiceConfig.getDirectory() + "/" + walletFile);

      String walletAddress = credentials.getAddress(); // address on bc
      String privateKey = encryptionService.encrypt(credentials.getEcKeyPair().getPrivateKey().toString());
      String jsonFilePath = walletServiceConfig.getDirectory() + File.separator + walletFile;


      HappyWallet wallet = HappyWallet.builder()
          .user(user)
          .address(walletAddress)
          .privateEncryptedKey(privateKey)
          .encryptedPassword(encryptionService.encrypt(walletPassword))
          .jsonFilePath(jsonFilePath)
          .build();

      return happyWalletRepository.save(wallet);

    } catch (Exception e) {
      log.error("Issue generating the wallet {}, {}", e.getMessage(), e.getStackTrace());
      throw new RuntimeException(e);
    }

  }

  public Optional<HappyWallet> getWalletForUser(User user){
    return happyWalletRepository.findByUser(user);
  }

  public Credentials getCredentialsForUser(String userName){
    HappyWallet happyWallet = getHappyWallet(userName);
    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      return Credentials.create(convertToHex(decryptedKey));
    } catch (Exception e) {
      log.error("Issue getting credentials {}, {}", e.getMessage(), e.getStackTrace());
      //TODO add proper exception management
      throw new RuntimeException(e);
    }
  }

  private HappyWallet getHappyWallet(String userName) {
    User user = userService.getUser(userName);
    Optional<HappyWallet> maybeHappyWallet = getWalletForUser(user);

    return maybeHappyWallet.orElseGet(() -> createWalletForUser(user));
  }

}
