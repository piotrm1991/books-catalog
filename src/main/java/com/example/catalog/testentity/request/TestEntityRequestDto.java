package com.example.catalog.testentity.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEntityRequestDto implements Serializable {

  @NotBlank
  @Pattern(regexp = "\\w+")
  @Size(max = 255)
  private String name;
}
