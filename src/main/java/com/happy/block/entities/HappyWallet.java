package com.happy.block.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.web3j.crypto.Credentials;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "happy_wallet", schema = "happy_db")
public class HappyWallet {

  @Id
  @Builder.Default
  private UUID walletUuid =  UUID.randomUUID();
  @OneToOne(optional = false)
  @JoinColumn(name = "user_uuid")
  private User user;

  private String address;
  @Column(nullable = false)
  private String privateEncryptedKey;
  private String jsonFilePath;
  @Column(nullable = false)
  private String encryptedPassword;

  @JsonIgnore
  @CreationTimestamp
  @Column(nullable = false)
  private ZonedDateTime createdDate;

  @Transient
  @JsonIgnore
  public Credentials credentials;

}
