package com.happy.block.contract;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.happy.block.common.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.evm.Configuration;
import org.web3j.evm.EmbeddedWeb3jService;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.DefaultGasProvider;


public class HappyNFTTest {

  private Web3j web3j;
  private Credentials credentials;

  @BeforeEach
  public void setup() {

    String KEY = "13823000531387681755528190435916773995044987532286517683790131231747372721684";
    credentials = Credentials.create(Utils.convertToHex(KEY));
    Configuration configuration = new Configuration(new Address(credentials.getAddress()), 10);
    this.web3j = Web3j.build(new EmbeddedWeb3jService(configuration));

  }

  @Test
  public void testContractDeployment() throws Exception {
    // Deploy HappyNFT contract on the simulated blockchain
    HappyNFT contract = HappyNFT.deploy(web3j, credentials, new DefaultGasProvider()).send();
    assertNotNull(contract.getContractAddress());

    System.out.println("Contract deployed at: " + contract.getContractAddress());
  }

  @Test
  public void testMintNFT() throws Exception {
    HappyNFT contract = HappyNFT.deploy(web3j, credentials, new DefaultGasProvider()).send();

    //Mint an NFT for the test user
    TransactionReceipt receipt = contract.mintNFT(credentials.getAddress()).send();
    assertTrue(receipt.isStatusOK());

    System.out.println("NFT minted successfully!");
  }

}
