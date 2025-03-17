package com.happy.block.controllers;

import com.happy.block.domain.raffle.BurnTicketRequest;
import com.happy.block.domain.raffle.RaffleParticipationRequest;
import com.happy.block.domain.raffle.TransferTicketRequest;
import com.happy.block.service.RaffleParticipantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/raffle")
@RequiredArgsConstructor
@Tag(
    name = "Participants can manage their raffle tickets",
    description = "Get a ticket, delete it or transfer it to another participant"
)
public class RaffleParticipantController {

  private final RaffleParticipantService raffleParticipantService;

  @PostMapping("/get-ticket/{user}")
  @Operation(summary = "Get a new ticket for a user", description = "get a new raffle ticket")
  public ResponseEntity<BigInteger> mintToken(@PathVariable String user, @RequestBody RaffleParticipationRequest request) {
    BigInteger tokenId = raffleParticipantService.mintRaffleTicket(user, request);
    return ResponseEntity.ok(tokenId);
  }

  @PostMapping("/transfer-ticket/{user}")
  @Operation(summary = "Transfer ticket", description = "Transfer ticket to another user")
  public ResponseEntity<String> transferToken(@PathVariable String user, @RequestBody TransferTicketRequest request) {
    String transactionHash = raffleParticipantService.transferRaffleToken(user, request);
    return ResponseEntity.ok(transactionHash);
  }

  @PostMapping("/burn-ticket/{user}")
  public ResponseEntity<String> burnToken(@PathVariable String user, @RequestBody BurnTicketRequest request) {
    String transactionHash = raffleParticipantService.burnRaffleTicket(user, request);
    return ResponseEntity.ok(transactionHash);
  }
}
