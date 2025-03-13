package com.happy.block.service;

import com.happy.block.config.BlockChainConfig;
import com.happy.block.contract.HappyNFT;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

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

  public String deployContract(Credentials credentials) throws Exception {
    HappyNFT contract = HappyNFT.deploy(web3j, credentials, new DefaultGasProvider()).send();
    return contract.getContractAddress();
  }

  /*public TransactionReceipt sendTransaction(String privateKey, String to, BigInteger value) throws Exception {
    Credentials credentials = Credentials.create(privateKey);
    RawTransactionManager txManager = new RawTransactionManager(web3j, credentials);

    TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
        web3j, TransactionManager.DEFAULT_POLLING_FREQUENCY, TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);

    BigInteger gasPrice = Convert.toWei("10", Convert.Unit.GWEI).toBigInteger();
    BigInteger gasLimit = BigInteger.valueOf(21000);

    RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
        BigInteger.ZERO, gasPrice, gasLimit, to, value);

    String txHash = txManager.signAndSend(rawTransaction);
    return receiptProcessor.waitForTransactionReceipt(txHash);
  }*/



}
