package com.happy.block.domain.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDao {

  @NotNull
  @NotEmpty
  private String username;

  @NotNull
  @NotEmpty
  private String ethAddress;

}
