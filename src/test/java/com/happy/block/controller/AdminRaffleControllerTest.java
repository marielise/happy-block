package com.happy.block.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.happy.block.controllers.AdminRaffleController;
import com.happy.block.domain.CreateRaffleRequest;
import com.happy.block.domain.PickWinnerRequest;
import com.happy.block.service.AdminRaffleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class AdminRaffleControllerTest {

  @Mock
  private AdminRaffleService raffleService;

  @InjectMocks
  private AdminRaffleController adminRaffleController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void createRaffle_ShouldReturnContractAddress() {
    // Arrange
    CreateRaffleRequest request = new CreateRaffleRequest();
    request.setRaffleName("Test Raffle");
    String user = "testUser";
    when(raffleService.createRaffle(request.getRaffleName(), user)).thenReturn("0x123");

    // Act
    ResponseEntity<String> response = adminRaffleController.createRaffle(user, request);

    // Assert
    assertNotNull(response);
    assertEquals("0x123", response.getBody());
    verify(raffleService, times(1)).createRaffle("Test Raffle", user);
  }

  @Test
  void pickWinner_ShouldReturnWinnerAddress() {
    // Arrange
    PickWinnerRequest request = new PickWinnerRequest();
    request.setRaffleName("MyRaffle");
    String user = "testUser";
    when(raffleService.pickWinner(request, user)).thenReturn("0xWinner");

    // Act
    ResponseEntity<String> response = adminRaffleController.pickWinner(user, request);

    // Assert
    assertNotNull(response);
    assertEquals("0xWinner", response.getBody());
    verify(raffleService, times(1)).pickWinner(request, user);
  }

}
