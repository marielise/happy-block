package com.happy.block.service;

import static com.happy.block.common.Utils.convertToHex;

import com.happy.block.domain.AccountBalance;
import com.happy.block.domain.ContractInfoDao;
import com.happy.block.domain.EstimatedCost;
import com.happy.block.entities.HappyWallet;
import com.happy.block.entities.NftContract;
import com.happy.block.entities.User;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
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
  private final NftService nftService;

  @SneakyThrows
  @Transactional
  public String mint(String userName, ContractInfoDao contractInfoDao) {
    log.info("Start minting contract address {} for user {}", contractInfoDao, userName);
    HappyWallet happyWallet = getHappyWallet(userName);

    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      Credentials credentials = Credentials.create(convertToHex(decryptedKey));

      EstimatedCost estimatedCost = blockchainService.happyNFTMintEstimate(credentials, contractInfoDao.getContractAddress());

      TransactionReceipt receipt = blockchainService.happyNFTMint(credentials, estimatedCost.getEstimatedGas(), contractInfoDao.getContractAddress());

      log.info("HappyNFT minted {}", receipt);

      return receipt.getTransactionHash();

    } catch (Exception e) {
      log.error("deploy contract error", e);
      throw new RuntimeException(e);
    }

  }

  //TODO Refactor to use Auth
  @Transactional(rollbackFor = Exception.class)
  public NftContract deployContract(String userName) {
    log.info("Deploy contract by user {}", userName);

    HappyWallet happyWallet = getHappyWallet(userName);

    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      Credentials credentials = Credentials.create(convertToHex(decryptedKey));

      EstimatedCost estimatedCost = blockchainService.happyNFTDeployEstimate(credentials);

      String address = blockchainService.deployContract(credentials, estimatedCost.getEstimatedGas());

      NftContract contract = nftService.save("HappyNFT", address);
      log.info("contract deployed {}", contract);

      return contract;

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

  public EstimatedCost estimateDeployTransactionFee(String userName) {
    log.info("Estimate deploy transaction fee by user {}", userName);

    HappyWallet happyWallet = getHappyWallet(userName);
    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      Credentials credentials = Credentials.create(convertToHex(decryptedKey));

      return blockchainService.happyNFTDeployEstimate(credentials);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * To evolve to be able to select the contract in db
   * @param userName user
   * @param contractInfoDao contract information
   * @return estimated cost EstimatedCost
   */
  public EstimatedCost estimateMintTransactionFee(String userName, ContractInfoDao contractInfoDao) {
    log.info("Estimate mint transaction fee by user {} for contract {}", userName, contractInfoDao);

    HappyWallet happyWallet = getHappyWallet(userName);
    try {
      String decryptedKey = encryptionService.decrypt(happyWallet.getPrivateEncryptedKey());
      Credentials credentials = Credentials.create(convertToHex(decryptedKey));

      return blockchainService.happyNFTMintEstimate(credentials, contractInfoDao.getContractAddress());

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
