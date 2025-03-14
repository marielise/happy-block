package com.happy.block.service;

import com.happy.block.entities.HappyWallet;
import com.happy.block.entities.User;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HappyService {

  //do not do anything
  private final BlockchainService blockchainService;
  private final UserService userService;
  private final WalletService walletService;
  private final EncryptionService encryptionService;

  @SneakyThrows
  public void mint(Principal principal) {
    log.info("Do something");
    String info = blockchainService.getBlockchainVersion();
    log.info("blockchain version is {}", info);
  }

  //TODO Refactor to use Auth
  public String deployContract(String userName) {
    log.info("Deploy contract by user {}", userName);

    User user = userService.getUser(userName);
    Optional<HappyWallet> maybeHappyWallet = walletService.getWalletForUser(user);

    HappyWallet happyWallet = maybeHappyWallet.orElse(walletService.createWalletForUser(user));

    try {

      //Credentials credentials = Credentials.create(happyWallet.getPrivateEncryptedKey());

      return blockchainService.deployContract(happyWallet.getCredentials());

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }

}
