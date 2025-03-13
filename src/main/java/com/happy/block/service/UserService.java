package com.happy.block.service;

import com.happy.block.domain.NewUserDao;
import com.happy.block.domain.UserRegistrationDao;
import com.happy.block.entities.User;
import com.happy.block.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User createUser(NewUserDao userDao){
    User user = User.builder()
        .username(userDao.getUserName())
        .build();

    user.setEthAddress(generateEthereumAddress());
    return saveUser(user);

  }

  public User registerUser(UserRegistrationDao userDao) throws BadRequestException {
    if (!isValidEthereumAddress(userDao.getEthAddress())) {
      log.error("Invalid Ethereum Address: {}", userDao);
      throw new BadRequestException("Invalid Ethereum Address");
    }

    User user = User.builder()
        .username(userDao.getUsername())
        .ethAddress(userDao.getEthAddress())
        .build();

    return saveUser(user);

  }

  public User saveUser(User user) {

    log.info("Save user {}", user);

    try{
      return userRepository.save(user);
    } catch (Exception e){
      log.error("Save user error", e);
      throw e;
    }
  }

  private String generateEthereumAddress() {
    try {
      ECKeyPair credentials = Keys.createEcKeyPair();
      return "0x" + Keys.getAddress(credentials);
    } catch (Exception e) {
      throw new RuntimeException("Error generating Ethereum address", e);
    }
  }

  // Ethereum address validation
  private boolean isValidEthereumAddress(String ethAddress) {
    return ethAddress != null && ethAddress.matches("^0x[a-fA-F0-9]{40}$");
  }

}
