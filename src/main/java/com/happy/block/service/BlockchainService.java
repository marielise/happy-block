package com.happy.block.service;

import com.happy.block.config.BlockChainConfig;
import com.happy.block.contract.HappyNFT;
import com.happy.block.domain.EstimatedCost;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

@Slf4j
@Service
public class BlockchainService {

  @Getter
  private final Web3j web3j;
  private final BlockChainConfig config;


  public BlockchainService(BlockChainConfig config) {
    this.config = config;

    web3j = Web3j.build(new HttpService("http://"+ this.config.getNodeHost()+":" +  this.config.getNodePort() ));
  }

  public String getBlockchainVersion() throws Exception {
    return web3j.web3ClientVersion().send().getWeb3ClientVersion();
  }


  public EthGetBalance getAccountBalance(String address) throws Exception {
    log.info("getAccountBalance address= {} ", address);
    return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
  }


  public EstimatedCost estimateDeployTransactionFee(Credentials credentials, String contractByteCode) throws Exception {

    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(Transaction.createContractTransaction(
            credentials.getAddress(), null, BigInteger.ZERO, contractByteCode))
        .send();

    return getEstimatedCostMapper(ethEstimateGas);

  }

  public EstimatedCost estimateBurnTransactionFee(Credentials credentials, String contractAddress, String contractByteCode) throws Exception {
    return estimateNFTCallTransaction(credentials, contractAddress, contractByteCode);
  }

  public EstimatedCost estimateMintTransactionFee(Credentials credentials, String contractAddress, String contractByteCode) throws Exception {
    return estimateNFTCallTransaction(credentials, contractAddress, contractByteCode);
  }


  /**
   * Generic function to estimate cost
   * @param credentials
   * @param contractAddress
   * @param functionData
   * @return
   * @throws Exception
   */
  public EstimatedCost estimateNFTCallTransaction(Credentials credentials, String contractAddress, String functionData) throws Exception {
    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(
        Transaction.createFunctionCallTransaction(
            credentials.getAddress(),
            null,
            BigInteger.ZERO,
            DefaultGasProvider.GAS_LIMIT,
            contractAddress,
            functionData)).send();

    return getEstimatedCostMapper(ethEstimateGas);
  }

  private EstimatedCost getEstimatedCostMapper(EthEstimateGas ethEstimateGas) throws Exception {
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


  /**
   * Generic function to mint NFT
   * @param credentials
   * @param estimatedGas
   * @param contractAddress
   * @return
   * @throws Exception
   */
  public TransactionReceipt contractMint(Credentials credentials, BigInteger estimatedGas, String contractAddress)
      throws Exception {
    BigInteger gasLimit = estimatedGas.multiply(BigInteger.TWO); //Multiply by 2
    HappyNFT contract = HappyNFT.load(contractAddress, web3j, credentials, new StaticGasProvider(BigInteger.ZERO, gasLimit));

    TransactionReceipt receipt = contract.mintNFT(credentials.getAddress()).send();
    log.info("NFT minted {}", receipt);

    return receipt;

  }

  public String transferGas(Credentials fromCredentials, Credentials toCredentials, BigInteger amount) {

    try {
      log.info("Transferring {} gas from {} to {}", amount, fromCredentials.getAddress(), toCredentials.getAddress());

      // Create transaction
      Transaction transaction = Transaction.createEtherTransaction(
          fromCredentials.getAddress(),
          null,
          web3j.ethGasPrice().send().getGasPrice(),
          DefaultGasProvider.GAS_LIMIT, // gas limit
          toCredentials.getAddress(),
          amount
      );

      // Sign and send transaction
      EthSendTransaction response = web3j.ethSendTransaction(transaction).send();


      if (response.hasError()) {
        log.error("Gas transfer error: {}", response.getError().getMessage());
        throw new RuntimeException("Gas transfer failed: " + response.getError().getMessage());
      }

      log.info("Transaction Hash: {}", response.getTransactionHash());

      return response.getTransactionHash();

    } catch (Exception e) {
      log.error("Failed to transfer gas: ", e);
      throw new RuntimeException("Error transferring gas", e);
    }

  }
}
