package com.happy.block.service;

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

}
