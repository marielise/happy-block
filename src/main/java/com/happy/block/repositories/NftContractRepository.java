package com.happy.block.repositories;

import com.happy.block.entities.NftContract;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftContractRepository extends JpaRepository<NftContract, UUID> {

  NftContract findByContractType(String contractType);

  NftContract findByContractTypeAndContractName(String contractType, String contractName);
}
