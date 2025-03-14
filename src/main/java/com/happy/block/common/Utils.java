package com.happy.block.common;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

public class Utils {

  private Utils() {
  }

  public static String generateEthereumAddress() {
    try {
      ECKeyPair credentials = Keys.createEcKeyPair();
      return "0x" + Keys.getAddress(credentials);
    } catch (Exception e) {
      throw new RuntimeException("Error generating Ethereum address", e);
    }
  }

  // Ethereum address validation
  public static boolean isValidEthereumAddress(String ethAddress) {
    return ethAddress != null && ethAddress.matches("^0x[a-fA-F0-9]{40}$");
  }

}
