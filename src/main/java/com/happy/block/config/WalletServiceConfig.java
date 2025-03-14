package com.happy.block.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "happy.wallet")
public class WalletServiceConfig {

  @Value("/wallet")
  private String directory;

}
