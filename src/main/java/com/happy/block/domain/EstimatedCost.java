package com.happy.block.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstimatedCost {

  private BigInteger estimatedGas;
  private BigInteger gasPriceWei;
  private BigInteger totalCostWei;
  private BigDecimal totalCostETH;

}
