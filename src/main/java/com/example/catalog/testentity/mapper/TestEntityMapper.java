package com.example.catalog.testentity.mapper;

import com.example.catalog.testentity.entity.TestEntity;
import com.example.catalog.testentity.request.TestEntityRequestDto;
import org.springframework.stereotype.Component;

@Component
//TODO: It is nice to use Java libraries to not map all fields manually. You can check out MapStruct
public class TestEntityMapper {

  public TestEntity mapToEntity(TestEntityRequestDto requestDto) {
    return TestEntity.builder()
        .name(requestDto.getName())
        .build();
  }
}
