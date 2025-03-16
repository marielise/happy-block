package com.happy.block.domain.raffle;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BurnTicketRequest {
  private String raffleName;
  private BigInteger tokenId;
}
