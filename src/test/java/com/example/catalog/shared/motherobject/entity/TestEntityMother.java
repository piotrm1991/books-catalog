package com.example.catalog.shared.motherobject.entity;

import com.example.catalog.testentity.entity.TestEntity;

import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestEntityMother {

  public static TestEntity.TestEntityBuilder base() {
    return TestEntity.builder()
        .id(1L)
        .name("name")
        .createdAt(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
        .updatedAt(LocalDateTime.of(2023, 1, 1, 0, 0, 0))
        ;
  }
}