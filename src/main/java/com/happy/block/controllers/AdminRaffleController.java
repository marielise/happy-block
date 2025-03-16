package com.happy.block.controllers;

import com.happy.block.domain.CreateRaffleRequest;
import com.happy.block.domain.PickWinnerRequest;
import com.happy.block.service.AdminRaffleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin-raffle")
@RequiredArgsConstructor
@Tag(name = "Raffle Controller", description = "Operations for managing NFT raffles.")
public class AdminRaffleController {

  private final AdminRaffleService raffleService;

  @PostMapping("/create/{user}")
  @Operation(summary = "Create a raffle", description = "Creates a new raffle with the given name associated with a user.")
  public ResponseEntity<String> createRaffle(@PathVariable String user, @RequestBody CreateRaffleRequest request) {
    String contractAddress = raffleService.createRaffle(request.getRaffleName(), user);
    return ResponseEntity.ok(contractAddress);
  }

  @PostMapping("/pick-winner/{user}")
  @Operation(summary = "Pick a raffle winner", description = "Selects a winner for the specified raffle contract.")
  public ResponseEntity<String> pickWinner(@PathVariable String user, @RequestBody PickWinnerRequest request) {
    String winner = raffleService.pickWinner(request, user);
    return ResponseEntity.ok(winner);
  }

}
