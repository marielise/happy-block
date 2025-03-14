package com.happy.block.service;

import com.happy.block.config.EncryptionServiceConfig;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EncryptionService {

  private final EncryptionServiceConfig encryptionServiceConfig;

  private static byte[] fixKeyLength(String key, int length) {
    byte[] keyBytes = key.getBytes();
    return Arrays.copyOf(keyBytes, length); // Use 16, 24, or 32 bytes
  }

  public  String encrypt(String data) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec(
        fixKeyLength(encryptionServiceConfig.getEncryptionKey(), 24), "AES"
    );
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
  }

  public  String decrypt(String data) throws Exception {
    Cipher cipher = Cipher.getInstance("AES");
    SecretKeySpec keySpec = new SecretKeySpec(fixKeyLength(encryptionServiceConfig.getEncryptionKey(), 24), "AES");
    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
  }

}
