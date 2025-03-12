package com.happy.block.repositories;

import com.happy.block.entities.PrivateKeyEntity;
import com.happy.block.entities.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateKeyRepository extends JpaRepository<PrivateKeyEntity, UUID> {
  Optional<PrivateKeyEntity> findByUser(User user);
}
