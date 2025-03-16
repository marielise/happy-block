package com.happy.block.service;

import com.happy.block.domain.TransferGasDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

  private final BlockchainService blockchainService;
  private final WalletService walletService;

  public String transferGas(TransferGasDao transferRequest) {
    //we suppose both user exist in the system (cheat code)  should not be in prod
    Credentials fromCredentials = walletService.getCredentialsForUser(transferRequest.getFromUser());
    Credentials toCredentials = walletService.getCredentialsForUser(transferRequest.getToUser());

    return blockchainService.transferGas(fromCredentials, toCredentials, transferRequest.getAmount());
  }
}
