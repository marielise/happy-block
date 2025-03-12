package com.happy.block.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Service
public class BlockchainService {
  private final Web3j web3j;

  //TODO manage proper config instead of hardcoded

  public BlockchainService() {
    web3j = Web3j.build(new HttpService("http://localhost:8545"));
  }

  public String getBlockchainVersion() throws Exception {
    return web3j.web3ClientVersion().send().getWeb3ClientVersion();
  }

}
