package com.happy.block.support;

import com.happy.block.contract.HappyRaffleNFT;
import com.happy.block.domain.EstimatedCost;
import com.happy.block.service.BlockchainService;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

@Service
public class HappyRaffleNFTSupport {

  public EstimatedCost happyRaffleMintEstimate(Credentials credentials, String contractAddress, BlockchainService service) throws Exception {
    String functionData = getHappyRaffleMintFunctionData();
    return service.estimateMintTransactionFee(credentials, contractAddress, functionData);
  }

  public EstimatedCost happyRaffleBurnTransaction(Credentials credentials, String contractAddress, BigInteger tokenId, BlockchainService service) throws Exception {
    String functionData = getHappyRaffleBurnFunctionData(tokenId);
    return service.estimateBurnTransactionFee(credentials, contractAddress, functionData);
  }

  public EstimatedCost happyRaffleTransferEstimate(Credentials credentials, String contractAddress, BigInteger tokenId, String toAddress, BlockchainService service) throws Exception {
    String functionData = getHappyRaffleTransferFunctionData(tokenId, toAddress);
    return service.estimateNFTCallTransaction(credentials, contractAddress, functionData);
  }

  public EstimatedCost happyRafflePickWinnerEstimate(Credentials credentials, String contractAddress, BlockchainService service) throws Exception {
    String functionData = getHappyRafflePickWinnerFunctionData();
    return service.estimateNFTCallTransaction(credentials, contractAddress, functionData);
  }

  public static String getHappyRaffleMintFunctionData() {
    Function function = new Function(
        "mintRaffleTicket",
        Collections.emptyList(),
        Collections.emptyList()
    );
    return FunctionEncoder.encode(function);
  }

  public static String getHappyRaffleBurnFunctionData(BigInteger tokenId) {
    Function function = new Function(
        "burnTicket",
        Arrays.asList(new Uint256(tokenId)),
        Collections.emptyList()
    );
    return FunctionEncoder.encode(function);
  }

  public static String getHappyRaffleTransferFunctionData(BigInteger tokenId, String toAddress) {
    Function function = new Function(
        "transferFrom",
        Arrays.asList(new Uint256(tokenId), new Utf8String(toAddress)),
        Collections.emptyList()
    );
    return FunctionEncoder.encode(function);
  }

  public static String getHappyRafflePickWinnerFunctionData() {
    Function function = new Function(
        "pickWinner",
        Collections.emptyList(),
        Collections.emptyList()
    );
    return FunctionEncoder.encode(function);
  }

  public String deployRaffleContract(Credentials credentials, BigInteger estimatedGas, BlockchainService service, String raffleName) throws Exception {
    BigInteger gasLimit = estimatedGas.add(estimatedGas.multiply(new BigInteger("20")).divide(new BigInteger("100")));
    HappyRaffleNFT contract = HappyRaffleNFT.deploy(
        service.getWeb3j(),
        credentials,
        new StaticGasProvider(DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT),
        raffleName // String raffle name instead of Utf8String
    ).send();
    return contract.getContractAddress();
  }

  public TransactionReceipt pickWinner(Credentials credentials, String contractAddress, BigInteger estimatedGas, BlockchainService blockchainService) throws Exception {
    HappyRaffleNFT raffleContract = HappyRaffleNFT.load(contractAddress,
        blockchainService.getWeb3j(),
        credentials,
        new StaticGasProvider(DefaultGasProvider.GAS_PRICE, estimatedGas));

    return raffleContract.pickWinner().send();
  }

  public String getWinnerAddress(Credentials credentials, String contractAddress, BigInteger estimatedGas, BlockchainService service) throws Exception {

    HappyRaffleNFT raffleContract = HappyRaffleNFT.load(
        contractAddress,
        service.getWeb3j(),
        credentials,
        new StaticGasProvider(DefaultGasProvider.GAS_PRICE, estimatedGas)
    );

    String winnerAddress = raffleContract.getWinner().send();

    return winnerAddress;
  }

  public EstimatedCost happyRaffleDeployEstimate(Credentials credentials, BlockchainService blockchainService) throws Exception {
    return blockchainService.estimateDeployTransactionFee(credentials, HappyRaffleNFT.BINARY);
  }

}
