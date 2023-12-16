package com.example.catalog.shared.motherobject.response;

import com.example.catalog.testentity.response.TestEntityResponseDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestEntityResponseDtoMother {

  public static TestEntityResponseDto.TestEntityResponseDtoBuilder base() {
    return TestEntityResponseDto.builder()
        .id(1L)
        .name("name")
        ;
  }
}