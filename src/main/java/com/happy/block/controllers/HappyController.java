package com.happy.block.controllers;

import com.happy.block.domain.AccountBalance;
import com.happy.block.domain.ContractInfoDao;
import com.happy.block.domain.EstimatedCost;
import com.happy.block.entities.NftContract;
import com.happy.block.service.HappyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path={"/api/happy"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HappyController {

    private final HappyService happyService;

    //TODO use Auth for user
    @PostMapping("/deploy/{user}")
    public NftContract deploy(@PathVariable String user){
        return happyService.deployContract(user);
    }

    @GetMapping("/balance/{user}")
    public AccountBalance getBalance(@PathVariable String user){
        return happyService.getBalance(user);
    }

    @PostMapping("/estimate-deploy/{user}")
    public EstimatedCost estimateFee(@PathVariable String user){
        return happyService.estimateDeployTransactionFee(user);
    }

    @PostMapping("/estimate-mint/{user}")
    public EstimatedCost estimateMintFee(@PathVariable String user, @RequestBody ContractInfoDao contractInfoDao){
        return happyService.estimateMintTransactionFee(user, contractInfoDao);
    }

    @PostMapping("/mint/{user}")
    public String mintContract(@PathVariable String user, @RequestBody ContractInfoDao contractInfoDao){
        return happyService.mint(user, contractInfoDao);
    }

}
