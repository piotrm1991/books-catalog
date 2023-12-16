package com.example.catalog.testentity.controller;

import com.example.catalog.testentity.entity.TestEntity;
import com.example.catalog.testentity.presenter.TestEntityPresenter;
import com.example.catalog.testentity.request.TestEntityRequestDto;
import com.example.catalog.testentity.response.TestEntityResponseDto;
import com.example.catalog.testentity.service.TestEntityService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = "/test-entities")
@RequiredArgsConstructor
@Validated
public class TestEntityController {

  private final TestEntityService service;
  private final TestEntityPresenter presenter;

  @GetMapping("/{id}")
  public ResponseEntity<TestEntityResponseDto> getById(
      @PathVariable Long id
  ) {
    log.info("Getting entity by id {}", id);
    TestEntity entity = service.getById(id);
    TestEntityResponseDto response = presenter.mapToDto(entity);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<TestEntityResponseDto> create(
      @RequestBody @Valid TestEntityRequestDto request
  ) {
    log.info("Creating test entity");
    TestEntity entity = service.create(request);
    TestEntityResponseDto response = presenter.mapToDto(entity);

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
