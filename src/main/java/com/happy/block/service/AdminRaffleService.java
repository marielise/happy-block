package com.happy.block.service;

import com.happy.block.domain.EstimatedCost;
import com.happy.block.domain.PickWinnerRequest;
import com.happy.block.entities.NftContract;
import com.happy.block.support.HappyRaffleNFTSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@Service
@Slf4j
@AllArgsConstructor
public class AdminRaffleService {

  public static final String RAFFLE = "RAFFLE";
  private final BlockchainService blockchainService;
  private final NftService nftService;
  private final HappyRaffleNFTSupport happyRaffleNFTSupport;
  private final WalletService walletService;

  @Transactional()
  public String createRaffle(String raffleName, String userName) {
    log.info("Creating raffle '{}' for user '{}'", raffleName, userName);

    Credentials credentials = walletService.getCredentialsForUser(userName);
    String contractAddress;

    try {
      EstimatedCost estimatedCost = happyRaffleNFTSupport.happyRaffleDeployEstimate(credentials, blockchainService);
      contractAddress = happyRaffleNFTSupport.deployRaffleContract(credentials,
          estimatedCost.getEstimatedGas(),
          blockchainService,
          raffleName);

    } catch (Exception e) {
      log.error("Failed to deploy raffle contract for user {}", userName, e);
      throw new RuntimeException("Raffle deployment failed", e);
    }

    NftContract nftContract = nftService.save(RAFFLE, contractAddress);
    log.info("Raffle contract deployed at {}", contractAddress);

    return contractAddress;
  }

  public String pickWinner(PickWinnerRequest request, String userName) {
    log.info("Picking winner for raffle '{}' by user '{}'", request.getRaffleName(), userName);
    Credentials credentials = walletService.getCredentialsForUser(userName);

    // Find the contract address associated with the raffle name
    NftContract nftContract = nftService.findByContractTypeAndName(RAFFLE, request.getRaffleName());
    if (nftContract == null) {
      throw new RuntimeException("No raffle found with name: " + request.getRaffleName());
    }

    try {
       EstimatedCost estimatedCost = happyRaffleNFTSupport.happyRafflePickWinnerEstimate(credentials,
           nftContract.getContractAddress(),
           blockchainService);

       TransactionReceipt receipt = happyRaffleNFTSupport.pickWinner(credentials,
           nftContract.getContractAddress(),
           estimatedCost.getEstimatedGas(),
           blockchainService);


      log.info("Winner picked, transaction hash: {}", receipt.getTransactionHash());

      String winnerAddress = happyRaffleNFTSupport.getWinnerAddress(credentials,
          nftContract.getContractAddress(),
          estimatedCost.getEstimatedGas(),
          blockchainService);

      log.info("Winner of the raffle '{}' is '{}'", request.getRaffleName(), winnerAddress);

      return winnerAddress;


    } catch (Exception e) {
      log.error("Failed to pick winner for raffle {}", request.getRaffleName(), e);
      throw new RuntimeException("Picking winner failed", e);
    }

  }
}
