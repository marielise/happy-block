package com.happy.block.domain;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferGasDao {

  private String fromUser;
  private String toUser;
  private BigInteger amount;

}
