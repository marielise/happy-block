package com.happy.block.controllers;

import com.happy.block.service.HappyService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path={"/api/happy"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HappyController {

    private final HappyService happyService;


    @GetMapping
    public String getHappy(Principal principal){
        happyService.mint(principal);
        return "happy";
    }

    //TODO use Auth for user
    @PostMapping("/deploy/{user}")
    public String deploy(@PathVariable String user){
        return happyService.deployContract(user);
    }

}
