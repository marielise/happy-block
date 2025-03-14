package com.happy.block.service;

import static com.happy.block.common.Utils.convertToHex;

import com.happy.block.domain.AccountBalance;
import com.happy.block.domain.EstimatedCost;
import com.happy.block.entities.HappyWallet;
import com.happy.block.entities.User;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.utils.Convert;

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

    HappyWallet happyWallet = getHappyWallet(userName);

    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      Credentials credentials = Credentials.create(convertToHex(decryptedKey));

      return blockchainService.deployContract(credentials);

    } catch (Exception e) {
      log.error("deploy contract error", e);
      throw new RuntimeException(e);
    }

  }

  private HappyWallet getHappyWallet(String userName) {
    User user = userService.getUser(userName);
    Optional<HappyWallet> maybeHappyWallet = walletService.getWalletForUser(user);

    return maybeHappyWallet.orElseGet(() -> walletService.createWalletForUser(user));
  }


  public AccountBalance getBalance(String userName) {
    log.info("Get balance by user {}", userName);
    HappyWallet happyWallet = getHappyWallet(userName);

    try {
      String address = happyWallet.getAddress();

      EthGetBalance balance = blockchainService.getAccountBalance(address);

      BigInteger balanceWei = balance.getBalance();
      BigDecimal balanceEth = Convert.fromWei(new BigDecimal(balanceWei), Convert.Unit.ETHER);

      return AccountBalance.builder()
          .balanceEth(balanceEth)
          .balanceWei(balanceWei)
          .build();

    } catch (Exception e) {
      log.error("get balance error", e);
      throw new RuntimeException(e);
    }

  }

  public EstimatedCost estimateTransactionFee(String userName) {
    log.info("Estimate transaction fee by user {}", userName);

    HappyWallet happyWallet = getHappyWallet(userName);
    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      Credentials credentials = Credentials.create(convertToHex(decryptedKey));

      return blockchainService.happyNFTEstimate(credentials);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
