package com.happy.block.service;

import com.happy.block.entities.NftContract;
import com.happy.block.repositories.NftContractRepository;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NftService {

  private final NftContractRepository nftContractRepository;

  public NftContract findById(UUID id) {
    return nftContractRepository.findById(id).orElse(null);
  }

  public NftContract findByContractType(String type) {
    log.info("find NftContract by contractType {}", type);
    return nftContractRepository.findByContractType(type);
  }

  public NftContract save(String type, String address) {
    NftContract nftContract = NftContract.builder()
        .contractAddress(address)
        .contractType(type)
        .build();
    return nftContractRepository.save(nftContract);
  }

  public NftContract save(NftContract nftContract) {
    return nftContractRepository.save(nftContract);
  }


}
