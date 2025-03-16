package com.happy.block.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nft_contracts", schema = "happy_db")
public class NftContract {

  @Id
  @Builder.Default
  private UUID contractUuid = UUID.randomUUID();

  @Column(nullable = false)
  private String contractType;

  @Column(nullable = false)
  private String contractAddress;

  @Column(nullable = false)
  private String contractName;

}
