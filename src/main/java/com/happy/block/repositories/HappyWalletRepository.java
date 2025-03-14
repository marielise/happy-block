package com.happy.block.repositories;

import com.happy.block.entities.HappyWallet;
import com.happy.block.entities.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HappyWalletRepository extends JpaRepository<HappyWallet, UUID> {
  Optional<HappyWallet> findByUser(User user);

}
