package com.happy.block.service;

import static com.happy.block.common.Utils.generateEthereumAddress;
import static com.happy.block.common.Utils.isValidEthereumAddress;

import com.happy.block.domain.NewUserDao;
import com.happy.block.domain.UserRegistrationDao;
import com.happy.block.entities.User;
import com.happy.block.repositories.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public User createUser(NewUserDao userDao){
    User user = User.builder()
        .username(userDao.getUserName())
        .build();

    user.setEthAddress(generateEthereumAddress());
    return saveUser(user);

  }

  @Transactional
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

  public User getUser(String userName){
    log.info("Get user by userName {}", userName);
    Optional<User> user = userRepository.findByUsername(userName);
    return user.orElse(null);
  }


}
