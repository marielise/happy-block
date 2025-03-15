package com.happy.block.service;

import com.happy.block.config.BlockChainConfig;
import com.happy.block.contract.HappyNFT;
import com.happy.block.domain.EstimatedCost;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

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

  public String deployContract (Credentials credentials, BigInteger estimatedGas) throws Exception {
    BigInteger gasLimit = estimatedGas.add(estimatedGas.multiply(new BigInteger("20")).divide(new BigInteger("100"))); //20%
    HappyNFT contract = HappyNFT.deploy(web3j, credentials,  BigInteger.ZERO, gasLimit).send();

    return contract.getContractAddress();
  }

  public EthGetBalance getAccountBalance(String address) throws Exception {
    log.info("getAccountBalance address= {} ", address);
    return web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
  }

  public EstimatedCost happyNFTDeployEstimate(Credentials credentials) throws Exception {
    return estimateDeployTransactionFee(credentials, HappyNFT.BINARY);
  }

  public EstimatedCost estimateDeployTransactionFee(Credentials credentials, String contractByteCode) throws Exception {

    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(Transaction.createContractTransaction(
            credentials.getAddress(), null, BigInteger.ZERO, contractByteCode))
        .send();

    return getEstimatedCost(ethEstimateGas);

  }

  public EstimatedCost estimateMintTransaction(Credentials credentials, String contractAddress, String functionData) throws Exception {
    EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(
        Transaction.createFunctionCallTransaction(
            credentials.getAddress(),
            null,
            BigInteger.ZERO,
            DefaultGasProvider.GAS_LIMIT,
            contractAddress,
            functionData)).send();

    return getEstimatedCost(ethEstimateGas);
  }

  private EstimatedCost getEstimatedCost(EthEstimateGas ethEstimateGas) throws Exception {
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

  public static String getHappyNFTMintFunctionData(String recipientAddress) {
    // Define function parameters
    Function function = new Function(
        "mintNFT", // Function name must match contract
        Arrays.asList(new Address(recipientAddress)), // Address parameter
        Collections.emptyList()
    );

    // Encode function call
    return FunctionEncoder.encode(function);
  }

  public EstimatedCost happyNFTMintEstimate(Credentials credentials,
      String contractAddress) throws Exception {
    String functionData = getHappyNFTMintFunctionData(credentials.getAddress());

    return estimateMintTransaction(credentials, contractAddress, functionData);

  }

  public TransactionReceipt happyNFTMint(Credentials credentials, BigInteger estimatedGas, String contractAddress)
      throws Exception {
    BigInteger gasLimit = estimatedGas.multiply(BigInteger.TWO); //Multiply by 2
    HappyNFT contract = HappyNFT.load(contractAddress, web3j, credentials, new StaticGasProvider(BigInteger.ZERO, gasLimit));

    TransactionReceipt receipt = contract.mintNFT(credentials.getAddress()).send();
    log.info("HappyNFT minted {}", receipt);

    return receipt;

  }
}
