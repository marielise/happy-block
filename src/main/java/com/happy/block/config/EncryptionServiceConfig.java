package com.happy.block.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "happy.encrypt")
public class EncryptionServiceConfig {

  @Value("some_328523598@#$%^&_value")
  private String encryptionKey;
}
