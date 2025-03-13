package com.happy.block.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "private_keys", schema = "happy_db")
public class PrivateKeyEntity {
  @Id
  @Builder.Default
  private UUID keyUuid =  UUID.randomUUID();

  @OneToOne
  @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid")
  private User user;


  @Column(nullable = false)
  private String encryptedKey;

  @JsonIgnore
  @CreationTimestamp
  @Column(nullable = false)
  private ZonedDateTime createdDate;
}
