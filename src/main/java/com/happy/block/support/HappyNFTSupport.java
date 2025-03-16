package com.happy.block.support;

import com.happy.block.contract.HappyNFT;
import com.happy.block.domain.EstimatedCost;
import com.happy.block.service.BlockchainService;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;

@Service
public class HappyNFTSupport {


  public EstimatedCost happyNFTMintEstimate(Credentials credentials, String contractAddress, BlockchainService service) throws Exception {
    String functionData = getHappyNFTMintFunctionData(credentials.getAddress());

    return service.estimateMintTransactionFee(credentials, contractAddress, functionData);
  }

  public EstimatedCost happyNFTBurnTransaction(Credentials credentials, String contractAddress, BigInteger tokenId, BlockchainService service) throws Exception {
    String functionData = getHappyNFTBurnFunctionData(tokenId);
    return service.estimateBurnTransactionFee(credentials, contractAddress, functionData);
  }

  public static String getHappyNFTMintFunctionData(String recipientAddress) {
    // Define function parameters
    Function function = new Function(
        "mintNFT", // Function name match contract function
        Arrays.asList(new Address(recipientAddress)),
        Collections.emptyList()
    );

    // Encode function call
    return FunctionEncoder.encode(function);
  }

  public static String getHappyNFTBurnFunctionData(BigInteger tokenId) {
    // Define function parameters
    Function function = new Function(
        "burnNFT",
        Arrays.asList(new Uint256(tokenId)),
        Collections.emptyList()
    );

    // Encode function call
    return FunctionEncoder.encode(function);
  }

  public String deployContract (Credentials credentials, BigInteger estimatedGas, BlockchainService service) throws Exception {
    BigInteger gasLimit = estimatedGas.add(estimatedGas.multiply(new BigInteger("20")).divide(new BigInteger("100"))); //20%
    HappyNFT contract = HappyNFT.deploy(service.getWeb3j(), credentials,  BigInteger.ZERO, gasLimit).send();

    return contract.getContractAddress();
  }


  public EstimatedCost happyNFTDeployEstimate(Credentials credentials, BlockchainService blockchainService) throws Exception {
    return blockchainService.estimateDeployTransactionFee(credentials, HappyNFT.BINARY);
  }

}
