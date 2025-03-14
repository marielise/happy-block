package com.happy.block.config;


import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BouncyCastleConfig {

  @Bean
  public BouncyCastleProvider bouncyCastleProvider() {
    BouncyCastleProvider provider = new BouncyCastleProvider();
    Security.addProvider(provider);
    return provider;
  }
}