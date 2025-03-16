package com.happy.block.controller;

import com.happy.block.controllers.AdminController;
import com.happy.block.domain.TransferGasDao;
import com.happy.block.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

  @Mock
  private AdminService adminService;

  @InjectMocks
  private AdminController adminController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testTransferGas() {
    //Given
    TransferGasDao transferRequest = new TransferGasDao();
    String mockTransactionHash = "0x123456789abcdef";

    when(adminService.transferGas(transferRequest)).thenReturn(mockTransactionHash);

    // Then
    ResponseEntity<String> response = adminController.transferGas(transferRequest);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(mockTransactionHash, response.getBody());

    verify(adminService, times(1)).transferGas(transferRequest);
  }
}
