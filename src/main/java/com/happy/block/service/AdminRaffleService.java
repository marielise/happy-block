package com.happy.block.service;

import com.happy.block.domain.PickWinnerRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AdminRaffleService {
  private final BlockchainService blockchainService;
  private final NftService nftService;

  public String createRaffle(String raffleName, String userName) {

  }

  public String pickWinner(PickWinnerRequest request, String userName) {
  }
}
