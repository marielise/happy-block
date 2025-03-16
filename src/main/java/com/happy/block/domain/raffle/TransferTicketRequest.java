package com.happy.block.domain.raffle;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferTicketRequest {

  private String raffleName;
  private BigInteger tokenId;
  private String toUser;

}
