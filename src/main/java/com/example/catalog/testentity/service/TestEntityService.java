package com.example.catalog.testentity.service;

import com.example.catalog.testentity.entity.TestEntity;
import com.example.catalog.testentity.mapper.TestEntityMapper;
import com.example.catalog.testentity.repository.TestEntityRepository;
import com.example.catalog.testentity.request.TestEntityRequestDto;
import com.example.catalog.testentity.validator.TestEntityValidator;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestEntityService {

  private final TestEntityRepository repository;
  private final TestEntityValidator validator;
  private final TestEntityMapper mapper;

  public TestEntity getById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("test_entity_not_found"));
  }

  public TestEntity create(TestEntityRequestDto request) {
    //FIXME: if you don't need custom validations just remove it. If you need it - reformat it
    validator.validate(request);

    TestEntity mappedEntity = mapper.mapToEntity(request);
    TestEntity savedEntity = repository.save(mappedEntity);

    return savedEntity;
  }
}
