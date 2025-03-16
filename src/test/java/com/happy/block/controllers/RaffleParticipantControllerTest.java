package com.happy.block.controllers;

import com.happy.block.domain.raffle.BurnTicketRequest;
import com.happy.block.domain.raffle.RaffleParticipationRequest;
import com.happy.block.domain.raffle.TransferTicketRequest;
import com.happy.block.service.RaffleParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RaffleParticipantControllerTest {

  @Mock
  private RaffleParticipantService raffleParticipantService;

  @InjectMocks
  private RaffleParticipantController raffleParticipantController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void mintToken_ShouldReturnTokenId() {
    // Arrange
    String user = "testUser";
    RaffleParticipationRequest request = new RaffleParticipationRequest();
    request.setRaffleName("TestRaffle");
    BigInteger mockTokenId = BigInteger.valueOf(12345);

    when(raffleParticipantService.mintRaffleTicket(user, request)).thenReturn(mockTokenId);

    // Act
    ResponseEntity<BigInteger> response = raffleParticipantController.mintToken(user, request);

    // Assert
    assertEquals(mockTokenId, response.getBody());
    verify(raffleParticipantService, times(1)).mintRaffleTicket(user, request);
  }

  @Test
  void transferToken_ShouldReturnTransactionHash() {
    // Arrange
    String user = "testUser";
    TransferTicketRequest request = new TransferTicketRequest();
    request.setRaffleName("TestRaffle");
    request.setTokenId(BigInteger.valueOf(12345));
    request.setToUser("recipientUser");

    String mockTxHash = "0xabcdef123456";

    when(raffleParticipantService.transferRaffleToken(user, request)).thenReturn(mockTxHash);

    // Act
    ResponseEntity<String> response = raffleParticipantController.transferToken(user, request);

    // Assert
    assertEquals(mockTxHash, response.getBody());
    verify(raffleParticipantService, times(1)).transferRaffleToken(user, request);
  }

  @Test
  void burnToken_ShouldReturnTransactionHash() {
    // Arrange
    String user = "testUser";
    BurnTicketRequest request = new BurnTicketRequest();
    request.setRaffleName("TestRaffle");
    request.setTokenId(BigInteger.valueOf(12345));

    String mockTxHash = "0xdeadbeef123456";

    when(raffleParticipantService.burnRaffleTicket(user, request)).thenReturn(mockTxHash);

    // Act
    ResponseEntity<String> response = raffleParticipantController.burnToken(user, request);

    // Assert
    assertEquals(mockTxHash, response.getBody());
    verify(raffleParticipantService, times(1)).burnRaffleTicket(user, request);
  }
}
