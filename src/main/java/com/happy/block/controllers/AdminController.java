package com.happy.block.controllers;

import com.happy.block.domain.TransferGasDao;
import com.happy.block.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Controller for POC", description = "Only for cheating and add gas to wallet")
public class AdminController {

  private final AdminService adminService;

  @Operation(summary = "Transfer gas from a mining user", description = "Just to help on the project, to handle multiple user with wallets")
  @PostMapping("/transfer-gas/{user}")
  public ResponseEntity<String> transferGas(@RequestBody TransferGasDao transferRequest) {
    String transactionHash = adminService.transferGas(transferRequest);
    return ResponseEntity.ok(transactionHash);
  }

}
