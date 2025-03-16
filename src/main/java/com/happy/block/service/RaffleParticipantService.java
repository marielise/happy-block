package com.happy.block.service;

import com.happy.block.domain.EstimatedCost;
import com.happy.block.domain.raffle.BurnTicketRequest;
import com.happy.block.domain.raffle.RaffleParticipationRequest;
import com.happy.block.domain.raffle.TransferTicketRequest;
import com.happy.block.entities.NftContract;
import com.happy.block.support.HappyRaffleNFTSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleParticipantService {

  public static final String RAFFLE = "RAFFLE";
  private final WalletService walletService;
  private final BlockchainService blockchainService;
  private final HappyRaffleNFTSupport happyRaffleNFTSupport;
  private final NftService nftService;

  @Transactional()
  public BigInteger mintRaffleTicket(String userName, RaffleParticipationRequest request) {
    log.info("Minting raffle ticket for user '{}' on raffle '{}'", userName, request);
    Credentials credentials = walletService.getCredentialsForUser(userName);

    // Get contract address from raffle name
    NftContract nftContract = nftService.findByContractTypeAndName(RAFFLE, request.getRaffleName());
    if (nftContract == null) {
      throw new RuntimeException("No raffle found with name: " + request.getRaffleName());
    }

    try {
      // Estimate gas cost
      EstimatedCost estimatedCost = happyRaffleNFTSupport.happyRaffleMintEstimate(credentials,
          nftContract.getContractAddress(),
          blockchainService);

      BigInteger totalCostWei = estimatedCost.getTotalCostWei();

      // Check account balance
      BigInteger balance = blockchainService.getAccountBalance(credentials.getAddress())
          .getBalance();
      if (balance.compareTo(totalCostWei) < 0) {
        throw new RuntimeException("Insufficient balance to get raffle ticket");
      }

      // Mint ticket
      TransactionReceipt receipt = happyRaffleNFTSupport.mintHappyRaffleTicket(credentials,
          estimatedCost.getEstimatedGas(),
          nftContract.getContractAddress(),
          blockchainService);

      log.info("Raffle ticket minted. Transaction hash: {}", receipt.getTransactionHash());

      // Extract token ID from the Transfer event
      BigInteger tokenId = happyRaffleNFTSupport.extractTokenIdFromReceipt(receipt);
      log.info("Minted raffle ticket ID: {}", tokenId);

      return tokenId;
    } catch (Exception e) {
      log.error("Failed to mint raffle ticket", e);
      throw new RuntimeException("Minting raffle ticket failed", e);
    }
  }


  public String transferRaffleToken(String fromUser, TransferTicketRequest request) {
    log.info("Transferring raffle ticket '{}' from '{}' to '{}'", request.getTokenId(), fromUser, request.getToUser());

    // Get contract address from raffle name
    NftContract nftContract = nftService.findByContractTypeAndName(RAFFLE, request.getRaffleName());
    if (nftContract == null) {
      throw new RuntimeException("No raffle found with name: " + request.getRaffleName());
    }

    Credentials fromCredentials = walletService.getCredentialsForUser(fromUser);
    Credentials toCredentials = walletService.getCredentialsForUser(request.getToUser());

    try {
      // Estimate gas cost
      EstimatedCost estimatedCost = happyRaffleNFTSupport.happyRaffleTransferEstimate(
          fromCredentials,
          nftContract.getContractAddress(),
          request.getTokenId(),
          toCredentials.getAddress(),
          blockchainService
      );

      BigInteger totalCostWei = estimatedCost.getTotalCostWei();

      // Check sender's balance
      BigInteger balance = blockchainService.getAccountBalance(fromCredentials.getAddress())
          .getBalance();
      if (balance.compareTo(totalCostWei) < 0) {
        throw new RuntimeException("Insufficient balance to get raffle ticket");
      }

      // Execute transfer
      TransactionReceipt receipt = happyRaffleNFTSupport.transferTicket(fromCredentials,
          estimatedCost.getEstimatedGas(),
          nftContract.getContractAddress(),
          request.getTokenId(),
          toCredentials.getAddress(),
          blockchainService
      );

      log.info("Raffle ticket transferred. Transaction hash: {}", receipt.getTransactionHash());
      return receipt.getTransactionHash();
    } catch (Exception e) {
      log.error("Failed to transfer raffle ticket", e);
      throw new RuntimeException("Transferring raffle ticket failed", e);
    }
  }

  public String burnRaffleTicket(String userName, BurnTicketRequest request) {
    log.info("Burning raffle ticket '{}' for user '{}'", request.getTokenId(), userName);
    Credentials credentials = walletService.getCredentialsForUser(userName);

    // Get contract address from raffle name
    NftContract nftContract = nftService.findByContractTypeAndName(RAFFLE, request.getRaffleName());
    if (nftContract == null) {
      throw new RuntimeException("No raffle found with name: " + request.getRaffleName());
    }

    try {
      // Estimate gas cost
      EstimatedCost estimatedCost = happyRaffleNFTSupport.happyRaffleBurnEstimate(
          credentials,
          nftContract.getContractAddress(),
          request.getTokenId(),
          blockchainService
      );
      BigInteger totalCostWei = estimatedCost.getTotalCostWei();

      // Check use balance
      BigInteger balance = blockchainService.getAccountBalance(credentials.getAddress()).getBalance();

      if (balance.compareTo(totalCostWei) < 0) {
        throw new RuntimeException("Insufficient balance to get raffle ticket");
      }

      // Execute burn transaction
      TransactionReceipt receipt = happyRaffleNFTSupport.burnTicket(credentials,
          estimatedCost.getEstimatedGas(),
          nftContract.getContractAddress(),
          request.getTokenId(),
          blockchainService);

      log.info("Raffle ticket burned. Transaction hash: {}", receipt.getTransactionHash());

      return receipt.getTransactionHash();
    } catch (Exception e) {
      log.error("Failed to burn raffle ticket", e);
      throw new RuntimeException("Burning raffle ticket failed", e);
    }
  }
}
