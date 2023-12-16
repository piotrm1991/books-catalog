package com.example.catalog.testentity.response;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEntityResponseDto implements Serializable {

  private Long id;
  private String name;
}
