package com.happy.block.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "happy")
public class BlockChainConfig {

  @Value("localhost")
  private String nodeHost;

  @Value("8545")
  private String nodePort;

}
