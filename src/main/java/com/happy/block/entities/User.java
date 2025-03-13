package com.happy.block.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "happy_db")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Builder.Default
    @Column(name = "user_uuid", updatable = false, nullable = false, unique = true)
    private UUID userUuid =  UUID.randomUUID();

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String ethAddress;


}
