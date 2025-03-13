package com.happy.block.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HappyService {

    //do not do anything
    private final BlockchainService blockchainService;

    @SneakyThrows
    public void mint(){
        log.info("Do something");
        String info = blockchainService.getBlockchainVersion();
        log.info("blockchain version is {}", info);
    }

}
