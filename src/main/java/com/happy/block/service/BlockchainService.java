package com.happy.block.service;

import com.happy.block.config.BlockChainConfig;
import com.happy.block.contract.HappyNFT;
import com.happy.block.domain.EstimatedCost;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Slf4j
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

  public String deployContract (Credentials credentials) throws Exception {
    HappyNFT contract = HappyNFT.deploy(web3j, credentials, new DefaultGasProvider() ).send();
    return contract.getContractAddress();
  }

  public EthGetBalance getAccountBalance(String address) throws Exception {
    log.info("getAccountBalance address={}", address);
    return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
  }

  public EstimatedCost happyNFTEstimate(Credentials credentials) throws Exception {
    return estimateTransactionFee(credentials, HappyNFT.BINARY);
  }

  public EstimatedCost estimateTransactionFee(Credentials credentials, String contractByteCode) throws Exception {
    log.info("address={}", credentials.getAddress());

    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(Transaction.createContractTransaction(
            credentials.getAddress(), null, BigInteger.ZERO, contractByteCode))
        .send();

    if (ethEstimateGas.getError() != null) {
      log.error(ethEstimateGas.getError().getMessage());
      throw new Exception(ethEstimateGas.getError().getMessage());
    }

    BigInteger estimatedGas = ethEstimateGas.getAmountUsed();
    BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice(); // Get current gas price

    BigInteger totalCostWei = estimatedGas.multiply(gasPrice);
    BigDecimal totalCostETH = new BigDecimal(totalCostWei).divide(BigDecimal.valueOf(1_000_000_000_000_000_000L)); // Convert to ETH

    log.info("Estimated Gas: {} " , estimatedGas);
    log.info("Gas Price (Wei):  {}", gasPrice);
    log.info("Total Cost (Wei): {}", totalCostWei);
    log.info("Total Cost (ETH): {}", totalCostETH);

    return EstimatedCost.builder()
        .estimatedGas(estimatedGas)
        .totalCostETH(totalCostETH)
        .gasPriceWei(gasPrice)
        .totalCostWei(totalCostWei)
        .build();

  }

}
