package com.example.catalog.testentity.presenter;

import com.example.catalog.testentity.entity.TestEntity;
import com.example.catalog.testentity.response.TestEntityResponseDto;
import org.springframework.stereotype.Component;

@Component
//TODO: It is nice to use Java libraries to not map all fields manually. You can check out MapStruct
public class TestEntityPresenter {

  public TestEntityResponseDto mapToDto(TestEntity entity) {
    return TestEntityResponseDto.builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();
  }
}
