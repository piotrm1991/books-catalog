package com.example.catalog.shared.motherobject.request;

import com.example.catalog.testentity.request.TestEntityRequestDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestEntityRequestDtoMother {

  public static TestEntityRequestDto.TestEntityRequestDtoBuilder base() {
    return TestEntityRequestDto.builder()
        .name("name")
        ;
  }
}