package com.happy.block.service;

import com.happy.block.entities.User;
import com.happy.block.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public User registerUser(String username, String ethAddress) {
    User user = new User();
    user.setUsername(username);
    user.setEthAddress(ethAddress);

    log.info("Save user {}", user);

    try{
      return userRepository.save(user);
    } catch (Exception e){
      log.error("Save user error", e);
      throw e;
    }
  }

}
