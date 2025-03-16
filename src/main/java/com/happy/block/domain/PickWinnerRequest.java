package com.happy.block.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickWinnerRequest {

  private String raffleAddress;
  private String raffleName;

}
