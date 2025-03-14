package com.happy.block.service;

import com.happy.block.config.BlockChainConfig;
import com.happy.block.contract.HappyNFT;
import java.math.BigInteger;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Service
public class BlockchainService {

  private final Web3j web3j;
  private final BlockChainConfig config;
  private final static String TOKEN_NAME = "HappyNFT";
  private final static String SYMBOL = "HNFT";
  private final static BigInteger GAS_INITIAL_SUPPLY = new BigInteger("999999999999");

  //TODO manage proper config instead of hardcoded

  public BlockchainService(BlockChainConfig config) {
    this.config = config;

    web3j = Web3j.build(new HttpService("http://"+ this.config.getNodeHost()+":" +  this.config.getNodePort() ));
  }

  public String getBlockchainVersion() throws Exception {
    return web3j.web3ClientVersion().send().getWeb3ClientVersion();
  }

  public String deployContract (Credentials credentials) throws Exception {
    HappyNFT contract = HappyNFT.deploy(web3j, credentials, new DefaultGasProvider() ).send();
    return contract.getContractAddress();
  }

}
