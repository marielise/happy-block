package com.happy.block.service;

import com.happy.block.entities.PrivateKeyEntity;
import com.happy.block.entities.User;
import com.happy.block.repositories.PrivateKeyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PrivateKeyService {

  private final PrivateKeyRepository privateKeyRepository;

  public void storeEncryptedKey(User user, String encryptedKey) {
    PrivateKeyEntity keyEntity = new PrivateKeyEntity();
    keyEntity.setUser(user);
    keyEntity.setEncryptedKey(encryptedKey);
    privateKeyRepository.save(keyEntity);
  }

}
