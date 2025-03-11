package com.happy.block.controllers;

import com.happy.block.service.HappyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path={"/api/happy"}, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class HappyController {

    private final HappyService happyService;


    @GetMapping
    public String getHappy(){
        happyService.mint();
        return "happy";
    }

}
