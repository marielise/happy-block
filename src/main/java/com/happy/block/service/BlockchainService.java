package com.happy.block.service;

import com.happy.block.config.BlockChainConfig;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Service
public class BlockchainService {

  private final Web3j web3j;
  private final BlockChainConfig config;

  //TODO manage proper config instead of hardcoded

  public BlockchainService(BlockChainConfig config) {
    this.config = config;

    web3j = Web3j.build(new HttpService("http://"+ this.config.getNodeHost()+":" +  this.config.getNodePort() ));
  }

  public String getBlockchainVersion() throws Exception {
    return web3j.web3ClientVersion().send().getWeb3ClientVersion();
  }

}
